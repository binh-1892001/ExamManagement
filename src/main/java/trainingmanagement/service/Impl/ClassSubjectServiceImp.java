package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
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
    public ClassSubject add(AClassSubjectRequest aClassSubjectRequest) throws CustomException {
        Optional<Classroom> classroomOptional = classroomService.getClassById(aClassSubjectRequest.getClassId());
        Optional<Subject> subjectOptional = subjectService.getById(aClassSubjectRequest.getSubjectId());
        ClassSubject classSubject = new ClassSubject();
        if (classroomOptional.isPresent() && subjectOptional.isPresent()){
            Classroom classroom = classroomOptional.get();
            Subject subject = subjectOptional.get();
            if(classSubjectRepository.findByClassroomAndSubject(classroom,subject)!=null){
                classSubject.setClassroom(classroom);
                classSubject.setSubject(subject);
                return classSubjectRepository.save(classSubject);
            }
            throw new CustomException("Classroom and Subject existed!");
        }
        throw new CustomException("Classroom or Subject not exist!");
    }

    @Override
    public ClassSubject update(AClassSubjectRequest aClassSubjectRequest,Long id) throws CustomException {
        Optional<Classroom> classroomOptional = classroomService.getClassById(aClassSubjectRequest.getClassId());
        Optional<Subject> subjectOptional = subjectService.getById(aClassSubjectRequest.getSubjectId());
        Optional<ClassSubject> classSubjectOptional = findById(id);
        if (classSubjectOptional.isPresent()){
            ClassSubject classSubject = classSubjectOptional.get();
            if (classroomOptional.isPresent() && subjectOptional.isPresent()){
                Classroom classroom = classroomOptional.get();
                Subject subject = subjectOptional.get();
                if(classSubjectRepository.findByClassroomAndSubject(classroom,subject)!=null){
                    classSubject.setClassroom(classroom);
                    classSubject.setSubject(subject);
                    return classSubjectRepository.save(classSubject);
                }
                throw new CustomException("Classroom and Subject existed!");
            }
            throw new CustomException("Classroom or Subject not exist!");
        }
        throw new CustomException("Classroom and Subject not exist!");
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

    @Override
    public ClassSubject findByClassroom(Classroom classroom) {
        return classSubjectRepository.findByClassroom(classroom);
    }
}
