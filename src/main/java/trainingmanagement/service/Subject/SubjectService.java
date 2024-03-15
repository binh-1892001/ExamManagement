package trainingmanagement.service.Subject;

import trainingmanagement.model.dto.request.admin.ASubjectRequest;
import trainingmanagement.model.dto.response.admin.ASubjectResponse;
import trainingmanagement.model.entity.Subject;
import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> getAllToList();
    List<ASubjectResponse> getAllSubjectResponsesToList();
    Optional<Subject> getById(Long subjectId);
    Subject save(Subject subject);
    Subject save(ASubjectRequest ASubjectRequest);
    Subject patchUpdate(Long subjectId, ASubjectRequest ASubjectRequest);
    List<ASubjectResponse> findBySubjectName(String className);
    void deleteById(Long subjectId);
    ASubjectResponse entityMap(Subject subject);
    Subject entityMap(ASubjectRequest ASubjectRequest);
}
