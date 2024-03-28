package trainingmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AExamRequest;
import trainingmanagement.model.dto.response.admin.AExamResponse;
import trainingmanagement.model.dto.response.teacher.TExamResponse;
import trainingmanagement.model.entity.Exam;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExamService {
    Page<Exam> getAllToList(Pageable pageable);
    Page<AExamResponse> getAllExamResponsesToList(Pageable pageable);
    Optional<Exam> getById(Long examId);
    Exam save(Exam exam);
    Exam save(AExamRequest examRequest);
    Exam patchUpdateExam(Long examId, AExamRequest examRequest) throws CustomException;
    void hardDeleteById(Long examId);
    void softDeleteById(Long examId) throws CustomException;
    Page<AExamResponse> searchByExamName(String examName, Pageable pageable);
    Exam entityAMap(AExamRequest examRequest);
    AExamResponse entityAMap(Exam exam);
    TExamResponse entityTMap(Exam exam);
    //*Lấy danh sách Exam với trạng thái Active (Teacher)
    Page<Exam> getAllExamsToListWithActiveStatus(Pageable pageable);
    Page<TExamResponse> getAllExamResponsesToListWithActiveStatus(Pageable pageable);
    //* Lấy ra Exam theo id với trạng thái Active (Teacher)
    Optional<Exam> getExamByIdWithActiveStatus(Long examId);
    Optional<TExamResponse> getExamResponsesByIdWithActiveStatus(Long examId);
    //* Lấy danh sách Exam theo thời gian tạo(createdDate)
    Page<AExamResponse> getAllExamByCreatedDate(LocalDate date, Pageable pageable);
    Page<AExamResponse> getAllExamFromDateToDate(String dateStart, String dateEnd, Pageable pageable);
    //* find by subjectId
    Page<AExamResponse> getAllBySubjectId(Long subjectId,Pageable pageable);
    //* find all By subject of student
    List<Exam> getAllExamBySubjectOfStudent();
}
