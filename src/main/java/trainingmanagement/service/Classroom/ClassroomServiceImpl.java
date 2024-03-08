package trainingmanagement.service.Classroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingmanagement.model.base.AuditableEntity;
import trainingmanagement.model.dto.ClassroomRequest;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Enum.EStatusClass;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.ClassroomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomServiceImpl implements ClassroomService{
    @Autowired
    private ClassroomRepository classroomRepository;
    @Override
    public Page<Classroom> getAll(Pageable pageable) {
        return classroomRepository.findAll(pageable);
    }

    @Override
    public Optional<Classroom> getById(Long classroomId) {
        return classroomRepository.findById(classroomId);
    }

    @Override
    public Classroom save(ClassroomRequest classroomRequest) {
        Classroom classroom = Classroom.builder()
                .nameClass(classroomRequest.getNameClass())
                .status(EStatusClass.NEW)

                .build();
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom save(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom patchUpdate(Long classroomId, ClassroomRequest classroomRequest) {
        Optional<Classroom> updateClassroom = getById(classroomId);
        if(updateClassroom.isPresent()) {
            Classroom classroom = updateClassroom.get();
            AuditableEntity auditableEntity = updateClassroom.get();
            if (auditableEntity.getCreatedDate() != null) {
                auditableEntity.setCreatedDate(auditableEntity.getCreatedDate());
            }
            if (classroomRequest.getNameClass() != null) {
                classroom.setNameClass(classroomRequest.getNameClass());
            }
            if (classroomRequest.getStatus() != null) {
                if (classroomRequest.getStatus().equalsIgnoreCase(EStatusClass.NEW.name()))
                    classroom.setStatus(EStatusClass.NEW);
                else if (classroomRequest.getStatus().equalsIgnoreCase(EStatusClass.OJT.name()))
                    classroom.setStatus(EStatusClass.OJT);
                else classroom.setStatus(EStatusClass.FINISH);
            }
            return save(classroom);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        classroomRepository.deleteById(id);
    }

    @Override
    public List<Classroom> searchByName(String keyword) {
        return classroomRepository.findByNameClass(keyword);
    }
}
