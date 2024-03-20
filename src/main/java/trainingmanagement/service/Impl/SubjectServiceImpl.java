package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.base.AuditableEntity;
import trainingmanagement.model.dto.request.admin.ASubjectRequest;
import trainingmanagement.model.dto.response.admin.ASubjectResponse;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.SubjectRepository;
import trainingmanagement.service.SubjectService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Override
    public List<Subject> getAllToList() {
        return subjectRepository.findAll();
    }

    @Override
    public List<ASubjectResponse> getAllSubjectResponsesToList() {
        return getAllToList().stream().map(this::entityMap).toList();
    }

    @Override
    public Optional<Subject> getById(Long subjectId) {
        return subjectRepository.findById(subjectId);
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject save(ASubjectRequest subjectRequest) {
        return subjectRepository.save(entityMap(subjectRequest));
    }

    @Override
    public Subject patchUpdate(Long subjectId, ASubjectRequest subjectRequest) {
        Optional<Subject> updateSubject = getById(subjectId);
        if (updateSubject.isPresent()) {
            Subject subject = updateSubject.get();
            AuditableEntity auditableEntity = updateSubject.get();
            if (auditableEntity.getCreatedDate() != null)
                auditableEntity.setCreatedDate(auditableEntity.getCreatedDate());
            if (subjectRequest.getSubjectName() != null)
                subject.setSubjectName(subjectRequest.getSubjectName());
            if (subjectRequest.getStatus() != null)
                subject.setStatus(EActiveStatus.valueOf(subjectRequest.getStatus()));
            return save(subject);
        }
        return null;
    }

    @Override
    public List<ASubjectResponse> findBySubjectName(String subjectName) {
        return subjectRepository.findBySubjectNameContainingIgnoreCase(subjectName)
                .stream().map(this::entityMap).toList();
    }

    @Override
    public void deleteById(Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }

    @Override
    public Subject entityMap(ASubjectRequest ASubjectRequest) {
        return Subject.builder()
                .subjectName(ASubjectRequest.getSubjectName())
                .status(EActiveStatus.valueOf(ASubjectRequest.getStatus()))
                .build();
    }

    @Override

    public List<ASubjectResponse> getAllByClassId(Long classId) {
        List<Subject> subjects = subjectRepository.getAllByClassId(classId);
        return subjects.stream().map(this::entityMap).toList();
    }

    @Override
    public ASubjectResponse getASubjectResponseById(Long subjectId) throws CustomException {
        Optional<Subject> optionalSubject = getById(subjectId);
        // ? Exception cần tìm thấy thì mới có thể chuyển thành Dto.
        if(optionalSubject.isEmpty()) throw new CustomException("Subject is not exists.");
        Subject subject = optionalSubject.get();
        return entityMap(subject);
    }

    @Override
    public ASubjectResponse entityMap(Subject subject) {
        return ASubjectResponse.builder()
                .subjectName(subject.getSubjectName())
                .status(subject.getStatus())
                .build();
    }
}
