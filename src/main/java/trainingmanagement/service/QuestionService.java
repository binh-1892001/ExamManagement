package trainingmanagement.service;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AQuestionOptionRequest;
import trainingmanagement.model.dto.request.admin.AQuestionRequest;
import trainingmanagement.model.dto.request.teacher.TQuestionOptionRequest;
import trainingmanagement.model.dto.request.teacher.TQuestionRequest;
import trainingmanagement.model.dto.response.admin.AQuestionResponse;
import trainingmanagement.model.dto.response.teacher.TQuestionResponse;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.EQuestionLevel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<Question> getAllToList();
    List<AQuestionResponse> getAllQuestionResponsesToList();
    List<TQuestionResponse> teacherGetAllQuestionResponsesToList();
    Optional<Question> getById(Long questionId);
    Question save(Question question);
    Question save(AQuestionRequest questionRequest);
    Question save(TQuestionRequest questionRequest);
    Question saveQuestionAndOption(AQuestionOptionRequest questionOptionRequest);
    Question saveQuestionAndOption(TQuestionOptionRequest questionOptionRequest);
    Question patchUpdateQuestion(Long questionId, AQuestionRequest questionRequest);
    void deleteById(Long questionId);
    List<AQuestionResponse> findByQuestionContent(String questionContent);
    Question entityAMap(AQuestionRequest questionRequest);
    Question entityTMap(TQuestionRequest questionRequest);
    AQuestionResponse entityAMap(Question question);
    TQuestionResponse entityTMap(Question question);
    List<AQuestionResponse> getAllByTest(Test test);
    List<Question> getAllQuestionByTest(Test test);
    List<TQuestionResponse> teacherGetAllByTest(Test test);
    List<AQuestionResponse> getAllByCreatedDate(LocalDate date);
    List<AQuestionResponse> getAllFromDayToDay(LocalDate dateStart, LocalDate dateEnd);
    List<AQuestionResponse> getAllByQuestionLevel(EQuestionLevel questionLevel);
    //* Lay ds cau hoi random
    List<AQuestionResponse> getAllByTestRandom(Test test);
}
