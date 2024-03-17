package trainingmanagement.service.ClassSubject;

import trainingmanagement.model.dto.request.admin.AClassSubjectRequest;
import trainingmanagement.model.entity.ClassSubject;

public interface ClassSubjectService {
    ClassSubject save(AClassSubjectRequest AClassSubjectRequest);
    void deleteById(Long id);
}
