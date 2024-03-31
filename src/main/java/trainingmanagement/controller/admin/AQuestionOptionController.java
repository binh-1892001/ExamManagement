package trainingmanagement.controller.admin;

import jakarta.transaction.Transactional;
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
import trainingmanagement.model.dto.request.admin.AOptionRequest;
import trainingmanagement.model.dto.request.admin.AQuestionOptionRequest;
import trainingmanagement.model.dto.response.admin.AQuestionResponse;
import trainingmanagement.model.dto.time.DateSearch;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.EQuestionLevel;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.OptionService;
import trainingmanagement.service.QuestionService;
import trainingmanagement.service.TestService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
            @PathVariable String testId) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            Long idTest = Long.parseLong(testId);
            Optional<Test> test = testService.getTestById(idTest);
            if (test.isEmpty()) {
                throw new CustomException("Test is not exists.");
            }
            List<AQuestionResponse> questionResponses = questionService.getAllByTest(test.get());
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

    //* Hien thi danh sach question va option theo ngay thang tao
    @PostMapping("/createdDateExam")
    public ResponseEntity<?> getAllQuestionAndOptionByCreateDate(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "contentQuestion", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestBody @Valid DateSearchCreatedDate dateSearchCreatedDate) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            LocalDate date = LocalDate.parse(dateSearchCreatedDate.getCreateDate());
            List<AQuestionResponse> questionResponses = questionService.getAllByCreatedDate(date);
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
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    //* Hien thi danh sach question va option theo khoang thoi gian
    @PostMapping("/fromDateToDate")
    public ResponseEntity<?> getAllQuestionAndOptionFromDateToDate(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "contentQuestion", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestBody @Valid DateSearch dateSearch) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            LocalDate dateStart = LocalDate.parse(dateSearch.getStartDate());
            LocalDate dateEnd = LocalDate.parse(dateSearch.getEndDate());
            List<AQuestionResponse> questionResponses = questionService.getAllFromDayToDay(dateStart, dateEnd);
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
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    //* Hien thi danh sach question va option theo level question
    @PostMapping("/levelQuestion")
    public ResponseEntity<?> getAllQuestionAndOptionByLevelQuestion(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "contentQuestion", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestParam(defaultValue = "EASY", name = "levelQuestion") String levelQuestion) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AQuestionResponse> questionResponses = null;
            if (levelQuestion.equalsIgnoreCase(String.valueOf(EQuestionLevel.EASY))) {
                questionResponses = questionService.getAllByQuestionLevel(EQuestionLevel.EASY);
            } else if (levelQuestion.equalsIgnoreCase(String.valueOf(EQuestionLevel.NORMAL))) {
                questionResponses = questionService.getAllByQuestionLevel(EQuestionLevel.NORMAL);
            } else if (levelQuestion.equalsIgnoreCase(String.valueOf(EQuestionLevel.DIFFICULTY))) {
                questionResponses = questionService.getAllByQuestionLevel(EQuestionLevel.DIFFICULTY);
            }
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
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    // * Get question by id.
    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable("questionId") String questionId) throws CustomException {
        try {
            Long idQuestion = Long.parseLong(questionId);
            Optional<Question> question = questionService.getById(idQuestion);
            if (question.isEmpty())
                throw new CustomException("Question is not exists.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            question.stream().map(questionService::entityAMap)
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    //* Them question va cac option
    @PostMapping("/addQuestion")
    public ResponseEntity<?> addQuestionAndOption(
            @RequestBody @Valid AQuestionOptionRequest questionOptionRequest) throws CustomException {
        try {
            Question question = questionService.saveQuestionAndOption(questionOptionRequest);
            AQuestionResponse questionResponse = questionService.entityAMap(question);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.CREATED.value(),
                            HttpStatus.CREATED.name(),
                            questionResponse
                    ), HttpStatus.CREATED);
        } catch (NullPointerException e) {
            throw new CustomException("All of input data must not be null");
        }
    }

    //* Update question and Option
    @Transactional
    @PostMapping("/{questionId}")
    public ResponseEntity<?> patchUpdateQuestionAndOption(
            @PathVariable("questionId") String questionId,
            @RequestBody @Valid AQuestionOptionRequest aQuestionOptionRequest) throws CustomException {
        try {
            Long idQuestion = Long.parseLong(questionId);
            Question question = questionService.patchUpdateQuestion(idQuestion, aQuestionOptionRequest.getQuestionRequest());
            optionService.deleteByQuestion(question);
            List<AOptionRequest> aOptionRequests = aQuestionOptionRequest.getOptionRequests();
            for (AOptionRequest aOptionRequest : aOptionRequests) {
                aOptionRequest.setQuestionId(question.getId());
                aOptionRequest.setStatus("ACTIVE");
                optionService.save(aOptionRequest);
            }
            List<Option> options = optionService.getAllByQuestion(question);
            question.setOptions(options);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            questionService.entityAMap(question)
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    //* delete question and option
    @Transactional
    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestionAndOption(
            @PathVariable("questionId") String questionId
    ) throws CustomException {
        try {
            Long idQuestion = Long.parseLong(questionId);
            Optional<Question> question = questionService.getById(idQuestion);
            if (question.isPresent()) {
                optionService.deleteByQuestion(question.get());
                questionService.deleteById(idQuestion);
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                "Delete Success"
                        ), HttpStatus.OK);
            }
            throw new CustomException("Questions is empty.");
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }
}
