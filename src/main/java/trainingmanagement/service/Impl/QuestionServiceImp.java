package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.dto.request.admin.AOptionRequest;
import trainingmanagement.model.dto.request.admin.AQuestionOptionRequest;
import trainingmanagement.model.dto.request.admin.AQuestionRequest;
import trainingmanagement.model.dto.response.admin.AQuestionResponse;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EQuestionLevel;
import trainingmanagement.model.enums.EQuestionType;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Test;
import trainingmanagement.repository.QuestionRepository;
import trainingmanagement.service.OptionService;
import trainingmanagement.service.QuestionService;
import trainingmanagement.service.TestService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {
    private final QuestionRepository questionRepository;
    private final OptionService optionService;
    private final TestService testService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Question> getAllToList() {
        return questionRepository.findAll();
    }

    @Override
    public List<AQuestionResponse> getAllQuestionResponsesToList() {
        return getAllToList().stream().map(this::entityMap).toList();
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
        return questionRepository.save(entityMap(questionRequest));
    }

    @Override
    public Question saveQuestionAndOption(AQuestionOptionRequest questionOptionRequest) {
        Question question = save(questionOptionRequest.getAQuestionRequest());
        List<AOptionRequest> AOptionRequests = questionOptionRequest.getAOptionRequests();
        for (AOptionRequest AOptionRequest : AOptionRequests){
            AOptionRequest.setQuestionId(question.getId());
            optionService.save(AOptionRequest);
        }
        List<Option> options = optionService.getAllByQuestion(question);
        question.setOptions(options);
        return question;
    }

    @Override
    public List<AQuestionResponse> findByQuestionContent(String questionContent) {
        return questionRepository.findAllByContentQuestionIsContainingIgnoreCase(questionContent)
                .stream().map(this::entityMap).toList();
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
            if (questionRequest.getContentQuestion() != null)
                question.setContentQuestion(questionRequest.getContentQuestion());
            if (questionRequest.getLevelQuestion() != null) {
                EQuestionLevel levelQuestion = switch (questionRequest.getLevelQuestion()) {
                    case "EASY" -> EQuestionLevel.EASY;
                    case "NORMAL" -> EQuestionLevel.NORMAL;
                    case "DIFFICULTY" -> EQuestionLevel.DIFFICULTY;
                    default -> null;
                };
                question.setLevelQuestion(levelQuestion);
            }
            if (questionRequest.getTypeQuestion() != null) {
                EQuestionType typeQuestion = switch (questionRequest.getTypeQuestion()) {
                    case "SINGLE" -> EQuestionType.SINGLE;
                    case "MULTIPLE" -> EQuestionType.MULTIPLE;
                    default -> null;
                };
                question.setTypeQuestion(typeQuestion);
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
    public Question entityMap(AQuestionRequest questionRequest) {
        EQuestionLevel levelQuestion = switch (questionRequest.getLevelQuestion()) {
            case "EASY" -> EQuestionLevel.EASY;
            case "NORMAL" -> EQuestionLevel.NORMAL;
            case "DIFFICULTY" -> EQuestionLevel.DIFFICULTY;
            default -> null;
        };
        EQuestionType typeQuestion = switch (questionRequest.getTypeQuestion()) {
            case "SINGLE" -> EQuestionType.SINGLE;
            case "MULTIPLE" -> EQuestionType.MULTIPLE;
            default -> null;
        };
        return Question.builder()
                .contentQuestion(questionRequest.getContentQuestion())
                .levelQuestion(levelQuestion)
                .typeQuestion(typeQuestion)
                .image(questionRequest.getImage())
                .status(EActiveStatus.ACTIVE)
                .test(testService.getTestById(questionRequest.getTestId()).get())
                .build();
    }

    @Override
    public AQuestionResponse entityMap(Question question) {
        return AQuestionResponse.builder()
                .questionId(question.getId())
                .contentQuestion(question.getContentQuestion())
                .levelQuestion(question.getLevelQuestion())
                .typeQuestion(question.getTypeQuestion())
                .image(question.getImage())
                .status(question.getStatus())
                .createdDate(question.getCreatedDate())
                .testName(question.getTest().getTestName())
                .options(question.getOptions().stream().map(optionService::entityMap).toList())
                .build();
    }

    @Override
    public List<AQuestionResponse> getAllByTest(Test test) {
        List<Question> questions = questionRepository.getAllByTest(test);
        return questions.stream().map(this::entityMap).toList();
    }

    @Override
    public List<AQuestionResponse> getAllByCreatedDate(LocalDate date) {
        List<Question> questions = questionRepository.getAllByCreatedDate(date);
        return questions.stream().map(this::entityMap).toList();
    }

    @Override
    public List<AQuestionResponse> getAllFromDayToDay(String dateStart, String dateEnd) {
        List<Question> questions = questionRepository.getAllFromDayToDay(dateStart,dateEnd);
        return questions.stream().map(this::entityMap).toList();
    }

}
