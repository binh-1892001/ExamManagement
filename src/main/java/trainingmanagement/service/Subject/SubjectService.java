package trainingmanagement.service.Subject;

import trainingmanagement.model.dto.request.admin.SubjectRequest;
import trainingmanagement.model.dto.response.admin.SubjectResponse;
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
}
