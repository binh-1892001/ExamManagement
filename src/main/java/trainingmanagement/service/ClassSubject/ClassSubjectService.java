package trainingmanagement.service.ClassSubject;

import trainingmanagement.model.dto.admin.request.ClassSubjectRequest;
import trainingmanagement.model.entity.ClassSubject;

public interface ClassSubjectService {
    ClassSubject save(ClassSubjectRequest classSubjectRequest);
    void deleteById(Long id);
}