package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.request.admin.AClassSubjectRequest;
import trainingmanagement.model.entity.ClassSubject;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.model.entity.UserClass;
import trainingmanagement.repository.ClassSubjectRepository;
import trainingmanagement.service.ClassSubjectService;
import trainingmanagement.service.ClassroomService;
import trainingmanagement.service.SubjectService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassSubjectServiceImp implements ClassSubjectService {
    private final ClassSubjectRepository classSubjectRepository;
    private final ClassroomService classroomService;
    private final SubjectService subjectService;

    @Override
    public ClassSubject add(AClassSubjectRequest aClassSubjectRequest) {
        Optional<Classroom> classroom = classroomService.getClassById(aClassSubjectRequest.getClassId());
        Optional<Subject> subject = subjectService.getById(aClassSubjectRequest.getSubjectId());
        ClassSubject classSubject = new ClassSubject();
        if (classroom.isPresent() && subject.isPresent()){
            classSubject.setClassroom(classroom.get());
            classSubject.setSubject(subject.get());
            return classSubjectRepository.save(classSubject);
        }
        return null;
    }

    @Override
    public ClassSubject update(AClassSubjectRequest aClassSubjectRequest,Long id) {
        Optional<Classroom> classroom = classroomService.getClassById(aClassSubjectRequest.getClassId());
        Optional<Subject> subject = subjectService.getById(aClassSubjectRequest.getSubjectId());
        Optional<ClassSubject> classSubjectOptional = findById(id);
        if (classSubjectOptional.isPresent()){
            ClassSubject classSubject = classSubjectOptional.get();
            if (classroom.isPresent() && subject.isPresent()){
                classSubject.setClassroom(classroom.get());
                classSubject.setSubject(subject.get());
                return classSubjectRepository.save(classSubject);
            }
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        classSubjectRepository.deleteById(id);
    }

    @Override
    public Optional<ClassSubject> findById(Long id) {
        return classSubjectRepository.findById(id);
    }


    @Override
    public List<ClassSubject> findSubjectByClassId(Long classId) {
        return classSubjectRepository.findSubjectByClassId(classId);
    }

    @Override
    public List<ClassSubject> findClassBySubjectId(Long subjectId) {
        return classSubjectRepository.findClassBySubjectId(subjectId);
    }
}
