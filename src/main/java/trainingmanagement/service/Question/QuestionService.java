package trainingmanagement.service.Question;

import trainingmanagement.model.dto.request.admin.QuestionRequest;
import trainingmanagement.model.dto.response.admin.QuestionResponse;
import trainingmanagement.model.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<Question> getAllToList();
    List<QuestionResponse> getAllQuestionResponsesToList();
    Optional<Question> getById(Long questionId);
    Question save(Question question);
    Question save(QuestionRequest questionRequest);
    Question patchUpdateQuestion(Long questionId, QuestionRequest questionRequest);
    void deleteById(Long questionId);
    List<QuestionResponse> findByQuestionContent(String questionContent);
    Question entityMap(QuestionRequest questionRequest);
    QuestionResponse entityMap(Question question);
}
