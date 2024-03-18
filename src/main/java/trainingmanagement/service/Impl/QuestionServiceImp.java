package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.dto.request.admin.AQuestionRequest;
import trainingmanagement.model.dto.response.admin.AQuestionResponse;
import trainingmanagement.model.enums.EQuestionLevel;
import trainingmanagement.model.enums.EQuestionType;
import trainingmanagement.model.entity.Question;
import trainingmanagement.repository.QuestionRepository;
import trainingmanagement.service.QuestionService;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {
    private final QuestionRepository questionRepository;
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
    public Question save(AQuestionRequest AQuestionRequest) {
        return questionRepository.save(entityMap(AQuestionRequest));
    }

    @Override
    public List<AQuestionResponse> findByQuestionContent(String questionContent) {
        return questionRepository.findAllByQuestionContentIsContainingIgnoreCase(questionContent)
                .stream().map(this::entityMap).toList();
    }
    @Override
    public void deleteById(Long questionId) {
        questionRepository.deleteById(questionId);
    }
    @Override
    public Question patchUpdateQuestion(Long questionId, AQuestionRequest AQuestionRequest) {
        Optional<Question> updateQuestion = questionRepository.findById(questionId);
        if(updateQuestion.isPresent()){
            Question question = updateQuestion.get();
            if(AQuestionRequest.getQuestionContent() != null)
                question.setQuestionContent(AQuestionRequest.getQuestionContent());
            if(AQuestionRequest.getQuestionLevel() != null){
                EQuestionLevel levelQuestion = switch (AQuestionRequest.getQuestionLevel()) {
                    case "EASY" -> EQuestionLevel.EASY;
                    case "NORMAL" -> EQuestionLevel.NORMAL;
                    case "DIFFICULTY" -> EQuestionLevel.DIFFICULTY;
                    default -> null;
                };
                question.setQuestionLevel(levelQuestion);
            }
            if(AQuestionRequest.getQuestionType() != null){
                EQuestionType typeQuestion = switch (AQuestionRequest.getQuestionType()) {
                    case "SINGLE" -> EQuestionType.SINGLE;
                    case "MULTIPLE" -> EQuestionType.MULTIPLE;
                    default -> null;
                };
                question.setQuestionType(typeQuestion);
            }
            if(AQuestionRequest.getImage() != null)
                question.setImage(AQuestionRequest.getImage());
            return questionRepository.save(question);
        }
        return null;
    }

    @Override
    public Question entityMap(AQuestionRequest AQuestionRequest) {
        EQuestionLevel levelQuestion = switch (AQuestionRequest.getQuestionLevel()) {
            case "EASY" -> EQuestionLevel.EASY;
            case "NORMAL" -> EQuestionLevel.NORMAL;
            case "DIFFICULTY" -> EQuestionLevel.DIFFICULTY;
            default -> null;
        };
        EQuestionType typeQuestion = switch (AQuestionRequest.getQuestionType()){
            case "SINGLE" -> EQuestionType.SINGLE;
            case "MULTIPLE" -> EQuestionType.MULTIPLE;
            default -> null;
        };
        return Question.builder()
            .questionContent(AQuestionRequest.getQuestionContent())
            .questionLevel(levelQuestion)
            .questionType(typeQuestion)
            .image(AQuestionRequest.getImage())
            .build();
    }

    @Override
    public AQuestionResponse entityMap(Question question) {
        return AQuestionResponse.builder()
            .questionId(question.getId())
            .questionContent(question.getQuestionContent())
            .questionLevel(question.getQuestionLevel())
            .questionType(question.getQuestionType())
            .image(question.getImage())
            .build();
    }
}
