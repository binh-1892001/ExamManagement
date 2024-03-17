package trainingmanagement.service.Question;

import trainingmanagement.model.dto.admin.request.QuestionOptionRequest;
import trainingmanagement.model.dto.admin.request.QuestionRequest;
import trainingmanagement.model.dto.admin.response.QuestionResponse;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<Question> getAllToList();
    List<QuestionResponse> getAllQuestionResponsesToList();
    Optional<Question> getById(Long questionId);
    Question save(Question question);
    Question save(QuestionRequest questionRequest);
    Question saveQuestionAndOption(QuestionOptionRequest questionOptionRequest);
    Question patchUpdateQuestion(Long questionId, QuestionRequest questionRequest);
    void deleteById(Long questionId);
    List<QuestionResponse> findByQuestionContent(String questionContent);
    Question entityMap(QuestionRequest questionRequest);
    QuestionResponse entityMap(Question question);
    List<QuestionResponse> getAllByTest(Test test);
    List<QuestionResponse> getAllByCreatedDate(LocalDate date);
    List<QuestionResponse> getAllFromDayToDay(String dateStart,String dateEnd);
}
