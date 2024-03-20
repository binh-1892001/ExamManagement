package trainingmanagement.service;

import trainingmanagement.model.dto.request.admin.AClassSubjectRequest;
import trainingmanagement.model.entity.ClassSubject;
import trainingmanagement.model.entity.UserClass;

import java.util.List;
import java.util.Optional;

public interface ClassSubjectService {

    ClassSubject add(AClassSubjectRequest aClassSubjectRequest);
    ClassSubject update(AClassSubjectRequest aClassSubjectRequest,Long id);
    void deleteById(Long id);
    Optional<ClassSubject> findById(Long id);
    List<ClassSubject> findSubjectByClassId(Long classId);
    List<ClassSubject> findClassBySubjectId(Long subjectId);

}
