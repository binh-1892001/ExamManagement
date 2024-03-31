package trainingmanagement.controller.student;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.response.admin.AQuestionResponse;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.QuestionService;
import trainingmanagement.service.TestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/student/question")
public class SQuestionOptionController {
    private final TestService testService;
    private final QuestionService questionService;
    private final CommonService commonService;

    // * lay danh sach question va option theo test
    @GetMapping("/tests/{testId}")
    public ResponseEntity<?> getAllQuestionAndOptionByTestRandom(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "contentQuestion", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable String testId) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            Long id = Long.parseLong(testId);
            Optional<Test> test = testService.getTestById(id);
            if (test.isEmpty()){
                throw new CustomException("Test is not exists.");
            }
            List<AQuestionResponse> questionResponses = questionService.getAllByTestRandom (test.get());
            Page<?> questions = commonService.convertListToPages(pageable, questionResponses);
            if (!questions.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                questions.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Questions page is empty.");
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Questions page is out of range.");
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }
}
