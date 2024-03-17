package trainingmanagement.service.Subject;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import trainingmanagement.model.base.AuditableEntity;
import trainingmanagement.model.dto.request.admin.ASubjectRequest;
import trainingmanagement.model.dto.response.admin.ASubjectResponse;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.SubjectRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService{
    private final SubjectRepository subjectRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
    public Subject save(ASubjectRequest ASubjectRequest) {
        return subjectRepository.save(entityMap(ASubjectRequest));
    }

    @Override
    public Subject patchUpdate(Long subjectId, ASubjectRequest ASubjectRequest) {
        Optional<Subject> updateSubject = getById(subjectId);
        if(updateSubject.isPresent()) {
            Subject subject = updateSubject.get();
            AuditableEntity auditableEntity = updateSubject.get();
            if (auditableEntity.getCreatedDate() != null)
                auditableEntity.setCreatedDate(auditableEntity.getCreatedDate());
            if (ASubjectRequest.getSubjectName() != null)
                subject.setSubjectName(ASubjectRequest.getSubjectName());
            if (ASubjectRequest.getEActiveStatus() != null)
                subject.setStatus(EActiveStatus.valueOf(ASubjectRequest.getEActiveStatus()));
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
            .status(EActiveStatus.valueOf(ASubjectRequest.getEActiveStatus()))
            .build();
    }

    @Override
    public List<ASubjectResponse> getAllByClassId(Long classId) {
        List<Subject> subjects = subjectRepository.getAllByClassId(classId);
        return subjects.stream().map(this::entityMap).toList();
    }

    @Override
    public ASubjectResponse entityMap(Subject subject) {
        return ASubjectResponse.builder()
            .subjectName(subject.getSubjectName())
            .build();
    }
}
