package trainingmanagement.service;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AExamRequest;
import trainingmanagement.model.dto.response.admin.AExamResponse;
import trainingmanagement.model.dto.response.admin.ASubjectResponse;
import trainingmanagement.model.dto.response.teacher.TExamResponse;
import trainingmanagement.model.entity.Exam;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExamService {
    List<Exam> getAllToList();
    List<AExamResponse> getAllExamResponsesToList();
    Optional<Exam> getById(Long examId);
    Exam save(Exam exam);
    Exam save(AExamRequest examRequest);
    Exam patchUpdateExam(Long examId, AExamRequest examRequest) throws CustomException;
    void hardDeleteById(Long examId);
    void softDeleteById(Long examId) throws CustomException;
    List<AExamResponse> searchByExamName(String examName);
    Exam entityAMap(AExamRequest examRequest);
    AExamResponse entityAMap(Exam exam);
    TExamResponse entityTMap(Exam exam);
    //*Lấy danh sách Exam với trạng thái Active (Teacher)
    List<Exam> getAllExamsToListWithActiveStatus();
    List<TExamResponse> getAllExamResponsesToListWithActiveStatus();
    //* Lấy ra Exam theo id với trạng thái Active (Teacher)
    Optional<Exam> getExamByIdWithActiveStatus(Long examId);
    Optional<TExamResponse> getExamResponsesByIdWithActiveStatus(Long examId);
    //* Lấy danh sách Exam theo thời gian tạo(createdDate)
    List<AExamResponse> getAllExamByCreatedDate(LocalDate date);
    List<AExamResponse> getAllExamFromDateToDate(String dateStart, String dateEnd);
    //* find by subjectId
    List<AExamResponse> getAllBySubjectId(Long subjectId);
}
