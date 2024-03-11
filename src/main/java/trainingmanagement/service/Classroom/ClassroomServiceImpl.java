package trainingmanagement.service.Classroom;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import trainingmanagement.model.base.AuditableEntity;
import trainingmanagement.model.dto.request.ClassRequest;
import trainingmanagement.model.dto.response.ClassResponse;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Enum.EStatusClass;
import trainingmanagement.repository.ClassroomRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService{
    private final ClassroomRepository classroomRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<Classroom> getAllToList() {
        return classroomRepository.findAll();
    }
    public List<ClassResponse> getAllClassResponsesToList(){
        return getAllToList().stream().map(this::entityMap).toList();
    }
    @Override
    public Optional<Classroom> getById(Long classroomId) {
        return classroomRepository.findById(classroomId);
    }
    @Override
    public Classroom save(Classroom classroom) {
        return classroomRepository.save(classroom);
    }
    @Override
    public Classroom save(ClassRequest classRequest) {
        return classroomRepository.save(entityMap(classRequest));
    }
    @Override
    public Classroom patchUpdate(Long classroomId, ClassRequest classRequest) {
        Optional<Classroom> updateClassroom = getById(classroomId);
        if(updateClassroom.isPresent()) {
            Classroom classroom = updateClassroom.get();
            AuditableEntity auditableEntity = updateClassroom.get();
            if (auditableEntity.getCreatedDate() != null)
                auditableEntity.setCreatedDate(auditableEntity.getCreatedDate());
            if (classRequest.getClassName() != null)
                classroom.setClassName(classRequest.getClassName());
            if (classRequest.getStatus() != null) {
                if (classRequest.getStatus().equalsIgnoreCase(EStatusClass.NEW.name()))
                    classroom.setStatus(EStatusClass.NEW);
                else if (classRequest.getStatus().equalsIgnoreCase(EStatusClass.OJT.name()))
                    classroom.setStatus(EStatusClass.OJT);
                else classroom.setStatus(EStatusClass.FINISH);
            }
            return save(classroom);
        }
        return null;
    }

    @Override
    public void deleteById(Long classId) {
        classroomRepository.deleteById(classId);
    }

    @Override
    public List<ClassResponse> findByClassName(String className) {
        return classroomRepository.findByClassNameContainingIgnoreCase(className)
                .stream().map(this::entityMap).toList();
    }
    @Override
    public Classroom entityMap(ClassRequest classRequest) {
        EStatusClass classStatus = switch (classRequest.getStatus()) {
            case "NEW" -> EStatusClass.NEW;
            case "OJT" -> EStatusClass.OJT;
            case "FINISH" -> EStatusClass.FINISH;
            default -> null;
        };
        return Classroom.builder()
            .className(classRequest.getClassName())
            .status(classStatus)
            .build();
    }
    @Override
    public ClassResponse entityMap(Classroom classroom) {
        return ClassResponse.builder()
                .className(classroom.getClassName())
                .status(classroom.getStatus())
                .build();
    }
}