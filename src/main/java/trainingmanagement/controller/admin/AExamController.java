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
            @RequestParam(defaultValue = "asc", name = "sortBy") String sortBy
    ) throws CustomException {
        Pageable pageable;
        if (sortBy.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AExamResponse> examResponses = examService.getAllExamResponsesToList();
            Page<?> exams = commonService.convertListToPages(pageable, examResponses);
            if (!exams.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                exams.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Exams page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Exams page is out of range.");
        }
    }
    // * Get Exam by id.
    @GetMapping("/{examId}")
    public ResponseEntity<?> getExamById(@PathVariable("examId") Long examId) throws CustomException {
        Optional<Exam> exam = examService.getById(examId);
        if(exam.isPresent())
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            exam.get()
                    ), HttpStatus.OK);
        throw new CustomException("Exam is not exists.");
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
            @PathVariable("examId") Long examId,
            @RequestBody @Valid AExamRequest examRequest) throws CustomException {
        Exam exam = examService.patchUpdateExam (examId, examRequest);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                exam
            ), HttpStatus.OK);
    }
    // * Delete Exam by id.
    @DeleteMapping("/{examId}")
    public ResponseEntity<?> deleteExamById(@PathVariable("examId") Long examId) throws CustomException {
        Optional<Exam> deleteExam = examService.getById(examId);
        if(deleteExam.isPresent()){
            examService.deleteById(examId);
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
    }
    // * Search all Exams By examName to pages.
    @GetMapping("/search")
    public ResponseEntity<?> findByExamName(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "className", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String sortBy
        ) throws CustomException {
        Pageable pageable;
        if (sortBy.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AExamResponse> examResponses = examService.searchByExamName(keyword);
            Page<?> exams = commonService.convertListToPages(pageable, examResponses);
            if (!exams.isEmpty()) {
                return new ResponseEntity<>(
                    new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        exams.getContent()
                    ), HttpStatus.OK);
            }
            throw new CustomException("Exams page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Exams page is out of range.");
        }
    }

    // Tìm kiếm theo ngày tạo Exam
    @PostMapping("/createdDateExam")
    public ResponseEntity<?> findByCreatedDate(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "contentQuestion", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestBody DateSearch dateSearch) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            LocalDate date = LocalDate.parse(dateSearch.getCreateDate());
            List<AExamResponse> examResponses = examService.getAllExamByCreatedDate(date);
            Page<?> questions = commonService.convertListToPages(pageable, examResponses);
            if (!questions.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                questions.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Exam page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Exam page is out of range.");
        }
    }

    //* lấy danh sách exam theo subjectId
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<?> getAllExamBySubjectIdToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "examName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable Long subjectId
    ) throws CustomException{
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AExamResponse> examResponses = examService.getAllBySubjectId (subjectId);
            Page<?> exams = commonService.convertListToPages(pageable, examResponses);
            if (!exams.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                exams.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Exams page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Exams page is out of range.");
        }
    }
}
