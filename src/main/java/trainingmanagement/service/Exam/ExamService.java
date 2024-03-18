package trainingmanagement.service.Exam;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AExamRequest;
import trainingmanagement.model.dto.response.admin.AExamResponse;
import trainingmanagement.model.entity.Exam;

import java.util.List;
import java.util.Optional;

public interface ExamService {
    List<Exam> getAllToList();
    List<AExamResponse> getAllExamResponsesToList();
    Optional<Exam> getById(Long examId);
    Exam save(Exam exam);
    Exam save(AExamRequest AExamRequest);
    Exam patchUpdateExam(Long examId, AExamRequest AExamRequest) throws CustomException;
    void deleteById(Long examId);
    List<AExamResponse> searchByExamName(String examName);
    Exam entityMap(AExamRequest AExamRequest);
    AExamResponse entityMap(Exam exam);
}
