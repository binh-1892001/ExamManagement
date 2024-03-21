package trainingmanagement.service;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.ASubjectRequest;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.dto.response.admin.ASubjectResponse;
import trainingmanagement.model.entity.Subject;
import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> getAllToList();
    List<ASubjectResponse> getAllSubjectResponsesToList();
    Optional<Subject> getById(Long subjectId);
    Subject save(Subject subject);
    Subject save(ASubjectRequest subjectRequest);
    Subject patchUpdate(Long subjectId, ASubjectRequest subjectRequest);
    List<ASubjectResponse> findBySubjectName(String className);
    void hardDeleteById(Long subjectId) throws CustomException;
    void softDeleteById(Long subjectId) throws CustomException;
    List<ASubjectResponse> getAllByClassId(Long classId);
    ASubjectResponse getASubjectResponseById(Long subjectId) throws CustomException;
    Subject entityAMap(ASubjectRequest subjectRequest);
    ASubjectResponse entityAMap(Subject subject);
}
