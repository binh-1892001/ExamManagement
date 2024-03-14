package trainingmanagement.service.Subject;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import trainingmanagement.model.base.AuditableEntity;
import trainingmanagement.model.dto.admin.request.SubjectRequest;
import trainingmanagement.model.dto.admin.response.SubjectResponse;
import trainingmanagement.model.dto.teacher.response.TSubjectResponse;
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
    public List<SubjectResponse> getAllSubjectResponsesToList() {
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
    public Subject save(SubjectRequest subjectRequest) {
        return subjectRepository.save(entityMap(subjectRequest));
    }

    @Override
    public Subject patchUpdate(Long subjectId, SubjectRequest subjectRequest) {
        Optional<Subject> updateSubject = getById(subjectId);
        if(updateSubject.isPresent()) {
            Subject subject = updateSubject.get();
            AuditableEntity auditableEntity = updateSubject.get();
            if (auditableEntity.getCreatedDate() != null)
                auditableEntity.setCreatedDate(auditableEntity.getCreatedDate());
            if (subjectRequest.getSubjectName() != null)
                subject.setSubjectName(subjectRequest.getSubjectName());
            if (subjectRequest.getStatus () != null)
                subject.setStatus ( EActiveStatus.valueOf ( subjectRequest.getStatus () ) );
            return save(subject);
        }
        return null;
    }

    @Override
    public List<SubjectResponse> findBySubjectName(String subjectName) {
        return subjectRepository.findBySubjectNameContainingIgnoreCase(subjectName)
                .stream().map(this::entityMap).toList();
    }
    @Override
    public List<TSubjectResponse> findBySubjectNameRoleTeacher(String subjectName) {
        return subjectRepository.findBySubjectNameContainingIgnoreCase ( subjectName )
                .stream ().map ( this::entityMapRoleTeacher ).toList ();
    }
    @Override
    public void deleteById(Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }

    @Override
    public Subject entityMap(SubjectRequest subjectRequest) {
        EActiveStatus activeStatus = switch (subjectRequest.getStatus ().toUpperCase()) {
            case "INACTIVE" -> EActiveStatus.ACTIVE;
            case "ACTIVE" -> EActiveStatus.INACTIVE;
            default -> null;
        };
        return Subject.builder()
            .subjectName(subjectRequest.getSubjectName())
            .status (activeStatus)
            .build();
    }

    @Override
    public List<SubjectResponse> getAllByClassId(Long classId) {
        List<Subject> subjects = subjectRepository.getAllByClassId(classId);
        return subjects.stream().map(this::entityMap).toList();
    }

    @Override
    public SubjectResponse entityMap(Subject subject) {
        return SubjectResponse.builder()
            .subjectName(subject.getSubjectName())
                .status ( subject.getStatus ().name () )
            .build();
    }
    @Override
    public TSubjectResponse entityMapRoleTeacher(Subject subject) {
        return TSubjectResponse.builder ()
                .subjectName ( subject.getSubjectName () )
                .build ();
    }
    //Lấy danh sách Subject với trạng thái Active
    @Override
    public List<Subject> getAllSubjectWithActiveStatus(){
        return subjectRepository.getAllByStatus (EActiveStatus.ACTIVE);
    }
    @Override
    public List<TSubjectResponse> getAllSubjectResponsesToListRoleTeacher() {
        return getAllSubjectWithActiveStatus ().stream ().map ( this::entityMapRoleTeacher ).toList ();
    }
    // Lấy ra Subject theo id với trạng thái Active
    @Override
    public Optional<Subject> getSubjectByIdWithActiveStatus(Long subjectId) {
        return subjectRepository.findByIdAndStatus (subjectId, EActiveStatus.ACTIVE);
    }

    @Override
    public Optional<TSubjectResponse> getSubjectResponsesByIdWithActiveStatus(Long subjectId) {
        Optional<Subject> TSubject = getSubjectByIdWithActiveStatus (subjectId);
        if (TSubject.isPresent()){
            Subject subject = TSubject.get();
            return Optional.ofNullable(entityMapRoleTeacher (subject));
        }
        return Optional.empty();
    }
}
