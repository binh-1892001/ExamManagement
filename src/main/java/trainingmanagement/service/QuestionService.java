package trainingmanagement.service;

import trainingmanagement.model.dto.request.admin.AQuestionOptionRequest;
import trainingmanagement.model.dto.request.admin.AQuestionRequest;
import trainingmanagement.model.dto.response.admin.AQuestionResponse;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.EQuestionLevel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<Question> getAllToList();
    List<AQuestionResponse> getAllQuestionResponsesToList();
    Optional<Question> getById(Long questionId);
    Question save(Question question);
    Question save(AQuestionRequest questionRequest);
    Question saveQuestionAndOption(AQuestionOptionRequest questionOptionRequest);
    Question patchUpdateQuestion(Long questionId, AQuestionRequest questionRequest);
    void deleteById(Long questionId);
    List<AQuestionResponse> findByQuestionContent(String questionContent);
    Question entityAMap(AQuestionRequest questionRequest);
    AQuestionResponse entityAMap(Question question);
    List<AQuestionResponse> getAllByTest(Test test);
    List<AQuestionResponse> getAllByCreatedDate(LocalDate date);
    List<AQuestionResponse> getAllFromDayToDay(String dateStart, String dateEnd);
    //* Lay ds cau hoi random
    List<AQuestionResponse> getAllByTestRandom(Test test);
    List<AQuestionResponse> getAllByQuestionLevel(EQuestionLevel questionLevel);
}
