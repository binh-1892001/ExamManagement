package trainingmanagement.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.request.admin.AQuestionRequest;
import trainingmanagement.model.dto.response.admin.AQuestionResponse;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.entity.Question;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.QuestionService;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/questions")
public class AQuestionController {
    private final QuestionService questionService;
    private final CommonService commonService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // * Get all questions to pages.
    @GetMapping
    public ResponseEntity<?> getAllQuestionsToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "contentQuestion", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        // ! Cần thêm Exception nếu như có 1 trường enum bằng null.
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AQuestionResponse> questionResponses = questionService.getAllQuestionResponsesToList();
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
        } catch (IllegalArgumentException e) {
            throw new CustomException("Questions page is out of range.");
        }
    }
    // * Get question by id.
    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable("questionId") Long questionId) throws CustomException{
        Optional<Question> question = questionService.getById(questionId);
        if(question.isEmpty())
            throw new CustomException("Class is not exists.");
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                question.get()
            ), HttpStatus.OK);
    }
    // * Create new question.
    @PostMapping
    public ResponseEntity<?> createNewQuestion(@RequestBody @Valid AQuestionRequest questionRequest) {
        Question question = questionService.save(questionRequest);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                question
            ), HttpStatus.CREATED);
    }
    //* Update question
    @PatchMapping("/{questionId}")
    public ResponseEntity<?> patchUpdateQuestion(
            @PathVariable("questionId") Long questionId,
            @RequestBody @Valid AQuestionRequest questionRequest) {
        Question question = questionService.patchUpdateQuestion(questionId, questionRequest);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                question
            ), HttpStatus.OK);
    }
    // * Delete an exists question.
    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable Long questionId) {
        // ! Cần kiểm tra question tồn tại thì mới cho phép xoá, không thì bắn CustomException.
        questionService.deleteById(questionId);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Delete question successfully."
            ), HttpStatus.OK);
    }
    // * Find Question by contentQuestion.
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "contentQuestion", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AQuestionResponse> questionResponses = questionService.findByQuestionContent(keyword);
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
        } catch (IllegalArgumentException e) {
            throw new CustomException("Questions page is out of range.");
        }
    }
}
