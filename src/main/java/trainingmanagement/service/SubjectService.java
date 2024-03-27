package trainingmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.ASubjectRequest;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.dto.response.admin.ASubjectResponse;
import trainingmanagement.model.dto.response.teacher.TSubjectResponse;
import trainingmanagement.model.entity.Subject;
import java.util.List;
import java.util.Optional;

public interface SubjectService {
    Page<Subject> getAllToList(Pageable pageable);
    Page<ASubjectResponse> getAllSubjectResponsesToList(Pageable pageable);
    Optional<Subject> getById(Long subjectId);
    Subject save(Subject subject);
    Subject save(ASubjectRequest subjectRequest);
    Subject patchUpdate(Long subjectId, ASubjectRequest subjectRequest);
    Page<ASubjectResponse> findBySubjectName(String className,Pageable pageable);
    void hardDeleteById(Long subjectId) throws CustomException;
    void softDeleteById(Long subjectId) throws CustomException;
    List<ASubjectResponse> getAllByClassId(Long classId);
    ASubjectResponse getASubjectResponseById(Long subjectId) throws CustomException;
    Subject entityAMap(ASubjectRequest subjectRequest);
    ASubjectResponse entityAMap(Subject subject);
    //Lấy danh sách Subject với trạng thái Active
    Page<Subject> getAllSubjectWithActiveStatus(Pageable pageable);
    Page<TSubjectResponse> getAllSubjectResponsesToListRoleTeacher(Pageable pageable);
    // Lấy ra Subject theo id với trạng thái Active
    Optional<Subject> getSubjectByIdWithActiveStatus(Long subjectId);
    Optional<TSubjectResponse> getSubjectResponsesByIdWithActiveStatus(Long subjectId);
    //Tìm kiếm subject theo tên
    Page<TSubjectResponse> findBySubjectNameRoleTeacher(String className,Pageable pageable);
    TSubjectResponse entityMapRoleTeacher(Subject subject);
    List<Subject> getAllSubjectByClassIdAndUserId();
}
