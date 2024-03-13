package trainingmanagement.service.Question;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.dto.request.admin.QuestionRequest;
import trainingmanagement.model.dto.response.admin.QuestionResponse;
import trainingmanagement.model.entity.Enum.ELevelQuestion;
import trainingmanagement.model.entity.Enum.ETypeQuestion;
import trainingmanagement.model.entity.Question;
import trainingmanagement.repository.QuestionRepository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService{
    private final QuestionRepository questionRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Question> getAllToList() {
        return questionRepository.findAll();
    }

    @Override
    public List<QuestionResponse> getAllQuestionResponsesToList() {
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
    public Question save(QuestionRequest questionRequest) {
        return questionRepository.save(entityMap(questionRequest));
    }

    @Override
    public List<QuestionResponse> findByQuestionContent(String questionContent) {
        return questionRepository.findAllByContentQuestionIsContainingIgnoreCase(questionContent)
                .stream().map(this::entityMap).toList();
    }
    @Override
    public void deleteById(Long questionId) {
        questionRepository.deleteById(questionId);
    }
    @Override
    public Question patchUpdateQuestion(Long questionId, QuestionRequest questionRequest) {
        Optional<Question> updateQuestion = questionRepository.findById(questionId);
        if(updateQuestion.isPresent()){
            Question question = updateQuestion.get();
            if(questionRequest.getContentQuestion() != null)
                question.setContentQuestion(questionRequest.getContentQuestion());
            if(questionRequest.getLevelQuestion() != null){
                ELevelQuestion levelQuestion = switch (questionRequest.getLevelQuestion()) {
                    case "EASY" -> ELevelQuestion.EASY;
                    case "NORMAL" -> ELevelQuestion.NORMAL;
                    case "DIFFICULTY" -> ELevelQuestion.DIFFICULTY;
                    default -> null;
                };
                question.setLevelQuestion(levelQuestion);
            }
            if(questionRequest.getTypeQuestion() != null){
                ETypeQuestion typeQuestion = switch (questionRequest.getTypeQuestion()) {
                    case "SINGLE" -> ETypeQuestion.SINGLE;
                    case "MULTIPLE" -> ETypeQuestion.MULTIPLE;
                    default -> null;
                };
                question.setTypeQuestion(typeQuestion);
            }
            if(questionRequest.getImage() != null)
                question.setImage(questionRequest.getImage());
            return questionRepository.save(question);
        }
        return null;
    }

    @Override
    public Question entityMap(QuestionRequest questionRequest) {
        ELevelQuestion levelQuestion = switch (questionRequest.getLevelQuestion()) {
            case "EASY" -> ELevelQuestion.EASY;
            case "NORMAL" -> ELevelQuestion.NORMAL;
            case "DIFFICULTY" -> ELevelQuestion.DIFFICULTY;
            default -> null;
        };
        ETypeQuestion typeQuestion = switch (questionRequest.getTypeQuestion()){
            case "SINGLE" -> ETypeQuestion.SINGLE;
            case "MULTIPLE" -> ETypeQuestion.MULTIPLE;
            default -> null;
        };
        return Question.builder()
            .contentQuestion(questionRequest.getContentQuestion())
            .levelQuestion(levelQuestion)
            .typeQuestion(typeQuestion)
            .image(questionRequest.getImage())
            .build();
    }

    @Override
    public QuestionResponse entityMap(Question question) {
        return QuestionResponse.builder()
            .questionId(question.getId())
            .contentQuestion(question.getContentQuestion())
            .levelQuestion(question.getLevelQuestion().name())
            .typeQuestion(question.getTypeQuestion().name())
            .image(question.getImage())
            .build();
    }
}
