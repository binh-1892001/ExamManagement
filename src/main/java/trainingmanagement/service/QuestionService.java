package trainingmanagement.service;

import trainingmanagement.model.dto.request.admin.AQuestionOptionRequest;
import trainingmanagement.model.dto.request.admin.AQuestionRequest;
import trainingmanagement.model.dto.response.admin.AQuestionResponse;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<Question> getAllToList();
    List<AQuestionResponse> getAllQuestionResponsesToList();
    Optional<Question> getById(Long questionId);
    Question save(Question question);
    Question save(AQuestionRequest AQuestionRequest);
    Question saveQuestionAndOption(AQuestionOptionRequest AQuestionOptionRequest);
    Question patchUpdateQuestion(Long questionId, AQuestionRequest AQuestionRequest);
    void deleteById(Long questionId);
    List<AQuestionResponse> findByQuestionContent(String questionContent);
    Question entityMap(AQuestionRequest AQuestionRequest);
    AQuestionResponse entityMap(Question question);
    List<AQuestionResponse> getAllByTest(Test test);
    List<AQuestionResponse> getAllByCreatedDate(LocalDate date);
    List<AQuestionResponse> getAllFromDayToDay(String dateStart, String dateEnd);
}
