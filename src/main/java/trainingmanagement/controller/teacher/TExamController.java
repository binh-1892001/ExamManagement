package trainingmanagement.controller.teacher;

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
import trainingmanagement.model.dto.response.teacher.TExamResponse;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.ExamService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/teacher/exams")
public class TExamController {
    private final ExamService examService;
    private final CommonService commonService;

    //Lấy danh sách Exam đã Active
    @GetMapping
    public ResponseEntity<?> getAllExamsToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "examName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "sortBy") String sortBy
    ) throws CustomException {
        try {
            Pageable pageable;
            if (sortBy.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<TExamResponse> examResponses = examService.getAllExamResponsesToListWithActiveStatus(pageable);
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
    //Tìm kiếm theo id các Exam đã Active
    @GetMapping("/{examId}")
    public ResponseEntity<?> getExamById(@PathVariable("examId") Long examId) throws CustomException {
        Optional<TExamResponse> exam = examService.getExamResponsesByIdWithActiveStatus(examId);
        if (exam.isPresent())
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            exam.get()
                    ), HttpStatus.OK);
        throw new CustomException("Exam is not exists.");
    }

    // * Search all Exams By CreatedDate
    // Tìm kiếm ở admin rồi thì không cần tìm kiếm ở teacher nữa đúng không anh
}