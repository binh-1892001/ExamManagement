package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.base.AuditableEntity;
import trainingmanagement.model.dto.request.admin.ASubjectRequest;
import trainingmanagement.model.dto.response.admin.ASubjectResponse;
import trainingmanagement.model.dto.response.teacher.TSubjectResponse;
import trainingmanagement.model.entity.ClassSubject;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.UserClass;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.SubjectRepository;
import trainingmanagement.security.UserDetail.UserLoggedIn;
import trainingmanagement.service.SubjectService;
import trainingmanagement.service.UserClassService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserClassService userClassService;
    private final UserLoggedIn userLogin;
    @Override
    public Page<Subject> getAllToList(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    @Override
    public Page<ASubjectResponse> getAllSubjectResponsesToList(Pageable pageable) {
        return getAllToList(pageable).map(this::entityAMap);
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
        return subjectRepository.save(entityAMap(subjectRequest));
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
    public Page<ASubjectResponse> findBySubjectName(String subjectName,Pageable pageable) {
        return subjectRepository.findBySubjectNameContainingIgnoreCase(subjectName,pageable).map(this::entityAMap);
    }

    @Override
    public void hardDeleteById(Long subjectId) throws CustomException {
        if(!subjectRepository.existsById(subjectId))
            throw new CustomException("Subject is not exists to delete.");
        subjectRepository.deleteById(subjectId);
    }

    @Override
    public void softDeleteById(Long subjectId) throws CustomException {
        // ? Exception cần tìm thấy thì mới có thể xoá mềm.
        Optional<Subject> deleteSubject = getById(subjectId);
        if(deleteSubject.isEmpty())
            throw new CustomException("Subject is not exists to delete.");
        Subject subject = deleteSubject.get();
        subject.setStatus(EActiveStatus.INACTIVE);
        subjectRepository.save(subject);
    }

    @Override
    public List<ASubjectResponse> getAllByClassId(Long classId) {
        List<Subject> subjects = subjectRepository.getAllByClassId(classId);
        return subjects.stream().map(this::entityAMap).toList();
    }
    @Override
    public ASubjectResponse getASubjectResponseById(Long subjectId) throws CustomException {
        Optional<Subject> optionalSubject = getById(subjectId);
        // ? Exception cần tìm thấy thì mới có thể chuyển thành Dto.
        if(optionalSubject.isEmpty()) throw new CustomException("Subject is not exists.");
        Subject subject = optionalSubject.get();
        return entityAMap(subject);
    }
    @Override
    public Subject entityAMap(ASubjectRequest subjectRequest) {
        return Subject.builder()
                .subjectName(subjectRequest.getSubjectName())
                .status(EActiveStatus.valueOf(subjectRequest.getStatus()))
                .build();
    }
    @Override
    public ASubjectResponse entityAMap(Subject subject) {
        return ASubjectResponse.builder()
                .subjectId(subject.getId())
                .subjectName(subject.getSubjectName())
                .status(subject.getStatus())
                .build();
    }

    //Lấy danh sách Subject với trạng thái Active
    @Override
    public Page<Subject> getAllSubjectWithActiveStatus(Pageable pageable){
        return subjectRepository.getAllByStatus(EActiveStatus.ACTIVE,pageable);
    }
    @Override
    public Page<TSubjectResponse> getAllSubjectResponsesToListRoleTeacher(Pageable pageable) {
        return getAllSubjectWithActiveStatus(pageable).map( this::entityMapRoleTeacher);
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
    @Override
    public Page<TSubjectResponse> findBySubjectNameRoleTeacher(String subjectName,Pageable pageable) {
        return subjectRepository.findBySubjectNameContainingIgnoreCase(subjectName,pageable).map(this::entityMapRoleTeacher);
    }
    @Override
    public TSubjectResponse entityMapRoleTeacher(Subject subject) {
        return TSubjectResponse.builder ()
                .subjectName ( subject.getSubjectName () )
                .build ();
    }

    @Override
    public List<Subject> getAllSubjectByClassIdAndUserId() {
        List<UserClass> userClasses = userClassService.findClassByStudent(userLogin.getUserLoggedIn().getId());
        List<Subject> subjects = new ArrayList<>();
        List<Classroom> classrooms = new ArrayList<>();
        for (UserClass userClass:userClasses){
            classrooms.add(userClass.getClassroom());
        }
        for (Classroom classroom:classrooms){
            for (ClassSubject classSubject:classroom.getClassSubjects()){
                subjects.add(classSubject.getSubject());
            }
        }
        return subjects;
    }
}
