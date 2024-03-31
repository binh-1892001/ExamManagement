package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AOptionRequest;
import trainingmanagement.model.dto.request.admin.AQuestionOptionRequest;
import trainingmanagement.model.dto.request.admin.AQuestionRequest;
import trainingmanagement.model.dto.request.teacher.TOptionRequest;
import trainingmanagement.model.dto.request.teacher.TQuestionOptionRequest;
import trainingmanagement.model.dto.request.teacher.TQuestionRequest;
import trainingmanagement.model.dto.response.admin.AQuestionResponse;
import trainingmanagement.model.dto.response.teacher.TQuestionResponse;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EQuestionLevel;
import trainingmanagement.model.enums.EQuestionType;
import trainingmanagement.repository.QuestionRepository;
import trainingmanagement.service.OptionService;
import trainingmanagement.service.QuestionService;
import trainingmanagement.service.TestService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {
    private final QuestionRepository questionRepository;
    private final OptionService optionService;
    private final TestService testService;

    @Override
    public List<Question> getAllToList() {
        return questionRepository.findAll();
    }

    @Override
    public List<AQuestionResponse> getAllQuestionResponsesToList() {
        return getAllToList().stream().map(this::entityAMap).toList();
    }

    @Override
    public List<TQuestionResponse> teacherGetAllQuestionResponsesToList() {
        return null;
    }

    @Override
    public Optional<Question> getById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question save(AQuestionRequest questionRequest) {
        return questionRepository.save(entityAMap(questionRequest));
    }

    @Override
    public Question save(TQuestionRequest questionRequest) {
        return questionRepository.save(entityTMap(questionRequest));
    }

    @Override
    public Question saveQuestionAndOption(AQuestionOptionRequest questionOptionRequest) {
        Question question = save(questionOptionRequest.getQuestionRequest());
        List<AOptionRequest> aOptionRequests = questionOptionRequest.getOptionRequests();
        for (AOptionRequest aOptionRequest : aOptionRequests) {
            aOptionRequest.setQuestionId(question.getId());
            aOptionRequest.setStatus("ACTIVE");
            optionService.save(aOptionRequest);
        }
        List<Option> options = optionService.getAllByQuestion(question);
        question.setOptions(options);
        return question;
    }

    @Override
    public Question saveQuestionAndOption(TQuestionOptionRequest questionOptionRequest) {
        Question question = save(questionOptionRequest.getQuestionRequest());
        List<TOptionRequest> tOptionRequests = questionOptionRequest.getOptionRequests();
        for (TOptionRequest tOptionRequest : tOptionRequests) {
            tOptionRequest.setQuestionId(question.getId());
            tOptionRequest.setStatus("ACTIVE");
            optionService.save(tOptionRequest);
        }
        List<Option> options = optionService.getAllByQuestion(question);
        question.setOptions(options);
        return question;
    }

    @Override
    public List<AQuestionResponse> findByQuestionContent(String questionContent) {
        return questionRepository.findAllByQuestionContentIsContainingIgnoreCase(questionContent)
                .stream().map(this::entityAMap).toList();
    }

    @Override
    public void deleteById(Long questionId) {
        questionRepository.deleteById(questionId);
    }

    @Override
    public Question patchUpdateQuestion(Long questionId, AQuestionRequest questionRequest) {
        Optional<Question> updateQuestion = questionRepository.findById(questionId);
        if (updateQuestion.isPresent()) {
            Question question = updateQuestion.get();
            if (questionRequest.getQuestionContent() != null)
                question.setQuestionContent(questionRequest.getQuestionContent());
            if (questionRequest.getQuestionLevel() != null) {
                EQuestionLevel questionLevel = switch (questionRequest.getQuestionLevel()) {
                    case "EASY" -> EQuestionLevel.EASY;
                    case "NORMAL" -> EQuestionLevel.NORMAL;
                    case "DIFFICULTY" -> EQuestionLevel.DIFFICULTY;
                    default -> null;
                };
                question.setQuestionLevel(questionLevel);
            }
            if (questionRequest.getQuestionType() != null) {
                EQuestionType questionType = switch (questionRequest.getQuestionType()) {
                    case "SINGLE" -> EQuestionType.SINGLE;
                    case "MULTIPLE" -> EQuestionType.MULTIPLE;
                    default -> null;
                };
                question.setQuestionType(questionType);
            }
            if (questionRequest.getImage() != null)
                question.setImage(questionRequest.getImage());
            if (questionRequest.getTestId() != null)
                question.setTest(testService.getTestById(questionRequest.getTestId()).get());
            return questionRepository.save(question);
        }
        return null;
    }
    @Override
    public List<AQuestionResponse> getAllByTest(Test test) {
        List<Question> questions = questionRepository.getAllByTest(test);
        return questions.stream().map(this::entityAMap).toList();
    }

    @Override
    public List<Question> getAllQuestionByTest(Test test) {
        return questionRepository.getAllByTest(test);
    }
    public List<TQuestionResponse> teacherGetAllByTest(Test test) {
        List<Question> questions = questionRepository.getAllByTest(test);
        return questions.stream().map(this::entityTMap).toList();
    }
    @Override
    public List<AQuestionResponse> getAllByCreatedDate(LocalDate date) {
        List<Question> questions = questionRepository.getAllByCreatedDate(date);
        return questions.stream().map(this::entityAMap).toList();
    }

    @Override
    public List<AQuestionResponse> getAllFromDayToDay(LocalDate dateStart, LocalDate dateEnd){
        List<Question> questions = questionRepository.getAllFromDateToDate(dateStart, dateEnd);
        return questions.stream().map(this::entityAMap).toList();
    }

    @Override
    public List<AQuestionResponse> getAllByQuestionLevel(EQuestionLevel questionLevel) {
        List<Question> questions = questionRepository.getAllByQuestionLevel(questionLevel);
        return questions.stream().map(this::entityAMap).toList();
    }
    @Override
    public Question entityAMap(AQuestionRequest questionRequest) {
        EQuestionLevel questionLevel = switch (questionRequest.getQuestionLevel()) {
            case "EASY" -> EQuestionLevel.EASY;
            case "NORMAL" -> EQuestionLevel.NORMAL;
            case "DIFFICULTY" -> EQuestionLevel.DIFFICULTY;
            default -> null;
        };
        EQuestionType questionType = switch (questionRequest.getQuestionType()) {
            case "SINGLE" -> EQuestionType.SINGLE;
            case "MULTIPLE" -> EQuestionType.MULTIPLE;
            default -> null;
        };
        return Question.builder()
                .questionContent(questionRequest.getQuestionContent())
                .questionLevel(questionLevel)
                .questionType(questionType)
                .image(questionRequest.getImage())
                .status(EActiveStatus.ACTIVE)
                .test(testService.getTestById(questionRequest.getTestId()).get())
                .build();
    }

    @Override
    public Question entityTMap(TQuestionRequest questionRequest) {
        EQuestionLevel questionLevel = switch (questionRequest.getLevelQuestion()) {
            case "EASY" -> EQuestionLevel.EASY;
            case "NORMAL" -> EQuestionLevel.NORMAL;
            case "DIFFICULTY" -> EQuestionLevel.DIFFICULTY;
            default -> null;
        };
        EQuestionType questionType = switch (questionRequest.getTypeQuestion()) {
            case "SINGLE" -> EQuestionType.SINGLE;
            case "MULTIPLE" -> EQuestionType.MULTIPLE;
            default -> null;
        };
        return Question.builder()
                .questionContent(questionRequest.getContentQuestion())
                .questionLevel(questionLevel)
                .questionType(questionType)
                .image(questionRequest.getImage())
                .status(EActiveStatus.ACTIVE)
                .test(testService.getTestById(questionRequest.getTestId()).get())
                .build();
    }

    @Override
    public AQuestionResponse entityAMap(Question question) {
        return AQuestionResponse.builder()
                .questionId(question.getId())
                .questionContent(question.getQuestionContent())
                .questionLevel(question.getQuestionLevel())
                .questionType(question.getQuestionType())
                .image(question.getImage())
                .status(question.getStatus())
                .createdDate(question.getCreatedDate())
                .testName(question.getTest().getTestName())
                .options(question.getOptions().stream().map(optionService::entityAMap).toList())
                .build();
    }

    @Override
    public TQuestionResponse entityTMap(Question question) {
        return TQuestionResponse.builder()
                .questionId(question.getId())
                .contentQuestion(question.getQuestionContent())
                .levelQuestion(question.getQuestionLevel())
                .typeQuestion(question.getQuestionType())
                .image(question.getImage())
                .status(question.getStatus())
                .createdDate(question.getCreatedDate())
                .test(question.getTest())
                .options(question.getOptions().stream().map(optionService::entityTMap).toList())
                .build();
    }

    @Override
    public List<AQuestionResponse> getAllByTestRandom(Test test) {
        List<Question> questions = questionRepository.getAllByTest(test);
        Collections.shuffle ( questions );
        return questions.stream().map(this::entityAMap).toList();
    }
}