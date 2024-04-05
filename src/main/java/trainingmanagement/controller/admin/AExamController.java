package trainingmanagement.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.time.DateSearchCreatedDate;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.request.admin.AExamRequest;
import trainingmanagement.model.dto.response.admin.AExamResponse;
import trainingmanagement.model.dto.time.DateSearch;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.entity.Exam;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.ExamService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/exams")
public class AExamController {
    private final ExamService examService;
    private final CommonService commonService;

    // * Get all exams to pages.
    @GetMapping
    public ResponseEntity<?> getAllExamsToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "examName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        try {
            Pageable pageable;
            if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<AExamResponse> examResponses = examService.getAllExamResponsesToList(pageable);
            if (examResponses.getContent().isEmpty()) throw new CustomException("exams page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            examResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }


    // * Get Exam by id.
    @GetMapping("/{examId}")
    public ResponseEntity<?> getExamById(@PathVariable("examId") String examId) throws CustomException {
        try {
            Long id = Long.parseLong(examId);
            Optional<Exam> exam = examService.getById(id);
            if (exam.isPresent())
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                exam.get()
                        ), HttpStatus.OK);
            throw new CustomException("Exam is not exists.");
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * Create new Exam.
    @PostMapping
    public ResponseEntity<?> createNewExam(@RequestBody @Valid AExamRequest examRequest) {
        Exam exam = examService.save(examRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        exam
                ), HttpStatus.CREATED);
    }

    // * Patch update Exam.
    @PatchMapping("/{examId}")
    public ResponseEntity<?> patchUpdateExam(
            @PathVariable("examId") String examId,
            @RequestBody @Valid AExamRequest examRequest) throws CustomException {
        try {
            Long id = Long.parseLong(examId);
            Exam exam = examService.patchUpdateExam(id, examRequest);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            exam
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * softDelete Exam by id.
    @DeleteMapping("{examId}")
    public ResponseEntity<?> softDeleteExamById(@PathVariable("examId") String examId) throws CustomException {
        try {
            Long id = Long.parseLong(examId);
            examService.softDeleteById(id);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            "Delete exam successfully."
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * hardDelete Exam by id.
    @DeleteMapping("delete/{examId}")
    public ResponseEntity<?> hardDeleteExamById(@PathVariable("examId") String examId) throws CustomException {
        try {
            Long id = Long.parseLong(examId);
            Optional<Exam> deleteExam = examService.getById(id);
            if (deleteExam.isPresent()) {
                examService.hardDeleteById(id);
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                "Delete exam successfully."
                        ), HttpStatus.OK);
            }
            // ? Xử lý Exception cần tìm được Exam theo id trước khi xoá trong Controller.
            throw new CustomException("Exam is not exists.");
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * Search all Exams By examName to pages.
    @GetMapping("/search")
    public ResponseEntity<?> findByExamName(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        try {
            Pageable pageable;
            if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<AExamResponse> examResponses = examService.searchByExamName(keyword, pageable);
            if (examResponses.getContent().isEmpty()) throw new CustomException("exams page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            examResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    //* Tìm kiếm theo ngày tạo Exam
    @PostMapping("/createdDateExam")
    public ResponseEntity<?> findByCreatedDate(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestBody @Valid DateSearchCreatedDate dateSearchCreatedDate) throws CustomException {
        try {
            Pageable pageable;
            if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            LocalDate date = LocalDate.parse(dateSearchCreatedDate.getCreateDate());
            Page<AExamResponse> examResponses = examService.getAllExamByCreatedDate(date, pageable);
            if (examResponses.getContent().isEmpty()) throw new CustomException("exams page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            examResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }

    }

    @PostMapping("/fromDateToDate")
    public ResponseEntity<?> findFromDateToDate(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestBody @Valid DateSearch dateSearch) throws CustomException {
        try {
            Pageable pageable;
            if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<AExamResponse> examResponses = examService.getAllExamFromDateToDate(dateSearch.getStartDate(), dateSearch.getEndDate(), pageable);
            if (examResponses.getContent().isEmpty()) throw new CustomException("exams page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            examResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    //* lấy danh sách exam theo subjectId
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<?> getAllExamBySubjectIdToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable String subjectId
    ) throws CustomException {
        try {
            Pageable pageable;
            if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Long idSubject = Long.parseLong(subjectId);
            Page<AExamResponse> examResponses = examService.getAllBySubjectId(idSubject, pageable);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            examResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }
}
