package trainingmanagement.service.ClassSubject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.admin.request.ClassSubjectRequest;
import trainingmanagement.model.entity.ClassSubject;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.ClassSubjectRepository;
import trainingmanagement.service.Classroom.ClassroomService;
import trainingmanagement.service.Subject.SubjectService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassSubjectServiceImp implements ClassSubjectService{
    private final ClassSubjectRepository classSubjectRepository;
    private final ClassroomService classroomService;
    private final SubjectService subjectService;

    @Override
    public ClassSubject save(ClassSubjectRequest classSubjectRequest) {
        Optional<Classroom> classroom = classroomService.getById(classSubjectRequest.getClassId());
        Optional<Subject> subject = subjectService.getById(classSubjectRequest.getSubjectId());
        ClassSubject classSubject = new ClassSubject();
        if (classroom.isPresent() && subject.isPresent()){
            classSubject.setClassroom(classroom.get());
            classSubject.setSubject(subject.get());
            return classSubjectRepository.save(classSubject);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        classSubjectRepository.deleteById(id);
    }
}
