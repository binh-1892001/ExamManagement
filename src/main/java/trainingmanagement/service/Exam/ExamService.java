package trainingmanagement.service.Exam;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.admin.request.ExamRequest;
import trainingmanagement.model.dto.admin.response.ExamResponse;
import trainingmanagement.model.entity.Exam;

import java.util.List;
import java.util.Optional;

public interface ExamService {
    List<Exam> getAllToList();
    List<ExamResponse> getAllExamResponsesToList();
    Optional<Exam> getById(Long examId);
    Exam save(Exam exam);
    Exam save(ExamRequest examRequest);
    Exam patchUpdateExam(Long examId, ExamRequest examRequest) throws CustomException;
    void deleteById(Long examId);
    List<ExamResponse> searchByExamName(String examName);
    Exam entityMap(ExamRequest examRequest);
    ExamResponse entityMap(Exam exam);
}
