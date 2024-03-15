package trainingmanagement.controller.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.Wrapper.ResponseWrapper;
import trainingmanagement.model.dto.admin.request.OptionRequest;
import trainingmanagement.model.dto.admin.request.QuestionOptionRequest;
import trainingmanagement.model.dto.admin.request.QuestionRequest;
import trainingmanagement.model.dto.admin.response.QuestionResponse;
import trainingmanagement.model.dto.time.DateSearch;
import trainingmanagement.model.entity.Enum.EHttpStatus;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Test;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.Option.OptionService;
import trainingmanagement.service.Question.QuestionService;
import trainingmanagement.service.Test.TestService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/question")
public class AQuestionOptionController {
    private final QuestionService questionService;
    private final CommonService commonService;
    private final TestService testService;
    private final OptionService optionService;

    // * lay danh sach question va option theo test
    @GetMapping("/test/{testId}")
    public ResponseEntity<?> getAllQuestionAndOptionByTest(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "contentQuestion", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable Long testId) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            Test test = testService.findById(testId);
            List<QuestionResponse> questionResponses = questionService.getAllByTest(test);
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

    //* Tim kiem danh sach question va option theo ngay thang tao
    @PostMapping("/date")
    public ResponseEntity<?> getAllQuestionAndOptionByCreateDate(
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
            List<QuestionResponse> questionResponses = questionService.getAllByCreatedDate(date);
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

    //* Tim kiem danh sach question va option theo khoang thoi gian
    @PostMapping("/FromDateToDate")
    public ResponseEntity<?> getAllQuestionAndOptionFromDateToDate(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "contentQuestion", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestBody DateSearch dateSearch) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<QuestionResponse> questionResponses = questionService.getAllFromDayToDay(dateSearch.getStartDate(), dateSearch.getEndDate());
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
    public ResponseEntity<?> getQuestionById(@PathVariable("questionId") Long questionId) throws CustomException {
        Optional<Question> question = questionService.getById(questionId);
        if (question.isEmpty())
            throw new CustomException("Class is not exists.");
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        question.stream().map(questionService::entityMap)
                ), HttpStatus.OK);
    }

    //* Them question va cac option
    @PostMapping("/addQuestion")
    public ResponseEntity<?> addQuestionAndOption(
            @RequestBody QuestionOptionRequest questionOptionRequest) {
        Question question = questionService.saveQuestionAndOption(questionOptionRequest);
        QuestionResponse questionResponse = questionService.entityMap(question);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        questionResponse
                ), HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/{questionId}")
    public ResponseEntity<?> patchUpdateQuestionAndOption(
            @PathVariable("questionId") Long questionId,
            @RequestBody QuestionOptionRequest questionOptionRequest) {
        Question question = questionService.patchUpdateQuestion(questionId, questionOptionRequest.getQuestionRequest());
        optionService.deleteByQuestion(question);
        List<OptionRequest> optionRequests = questionOptionRequest.getOptionRequests();
        for (OptionRequest optionRequest : optionRequests) {
            optionRequest.setQuestionId(question.getId());
            optionService.save(optionRequest);
        }
        List<Option> options = optionService.getAllByQuestion(question);
        question.setOptions(options);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        questionService.entityMap(question)
                ), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestionAndOption(
            @PathVariable("questionId") Long questionId) throws CustomException {
        Optional<Question> question = questionService.getById(questionId);
        if (question.isPresent()) {
            optionService.deleteByQuestion(question.get());
            questionService.deleteById(questionId);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            "Delete Success"
                    ), HttpStatus.OK);
        }
        throw new CustomException("Questions is empty.");
    }
}
