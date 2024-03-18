package trainingmanagement.service;

import trainingmanagement.model.dto.request.admin.AQuestionRequest;
import trainingmanagement.model.dto.response.admin.AQuestionResponse;
import trainingmanagement.model.entity.Question;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<Question> getAllToList();
    List<AQuestionResponse> getAllQuestionResponsesToList();
    Optional<Question> getById(Long questionId);
    Question save(Question question);
    Question save(AQuestionRequest AQuestionRequest);
    Question patchUpdateQuestion(Long questionId, AQuestionRequest AQuestionRequest);
    void deleteById(Long questionId);
    List<AQuestionResponse> findByQuestionContent(String questionContent);
    Question entityMap(AQuestionRequest AQuestionRequest);
    AQuestionResponse entityMap(Question question);
}
