package trainingmanagement.service.Subject;

import trainingmanagement.model.dto.admin.request.SubjectRequest;
import trainingmanagement.model.dto.admin.response.SubjectResponse;
import trainingmanagement.model.dto.teacher.response.TSubjectResponse;
import trainingmanagement.model.entity.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> getAllToList();

    List<SubjectResponse> getAllSubjectResponsesToList();

    Optional<Subject> getById(Long subjectId);

    Subject save(Subject subject);

    Subject save(SubjectRequest subjectRequest);

    Subject patchUpdate(Long subjectId, SubjectRequest subjectRequest);

    List<SubjectResponse> findBySubjectName(String className);

    void deleteById(Long subjectId);

    SubjectResponse entityMap(Subject subject);

    Subject entityMap(SubjectRequest subjectRequest);

    List<SubjectResponse> getAllByClassId(Long classId);

    //Lấy danh sách Subject với trạng thái Active
    List<Subject> getAllSubjectWithActiveStatus();
    List<TSubjectResponse> getAllSubjectResponsesToListRoleTeacher();
    // Lấy ra Subject theo id với trạng thái Active
    Optional<Subject> getSubjectByIdWithActiveStatus(Long subjectId);
    Optional<TSubjectResponse> getSubjectResponsesByIdWithActiveStatus(Long subjectId);
    //Tìm kiếm subject theo tên
    List<TSubjectResponse> findBySubjectNameRoleTeacher(String className);

    TSubjectResponse entityMapRoleTeacher(Subject subject);
}