package trainingmanagement.service.Exam;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.admin.request.ExamRequest;
import trainingmanagement.model.dto.admin.response.ExamResponse;
import trainingmanagement.model.dto.teacher.response.ExamResponseTeacher;
import trainingmanagement.model.entity.Exam;

import java.time.LocalDate;
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
    ExamResponseTeacher entityMapTeacher(Exam exam);

    //Lấy danh sách Exam với trạng thái Active (Teacher)
    List<Exam> getAllExamsToListWithActiveStatus();
    List<ExamResponseTeacher> getAllExamResponsesToListWithActiveStatus();
    // Lấy ra Exam theo id với trạng thái Active (Teacher)
    Optional<Exam> getExamByIdWithActiveStatus(Long examId);
    Optional<ExamResponseTeacher> getExamResponsesByIdWithActiveStatus(Long examId);
    //Lấy danh sách Exam theo thời gian tạo(createdDate)
    List<ExamResponse> getAllExamByCreatedDate(LocalDate date);
}
