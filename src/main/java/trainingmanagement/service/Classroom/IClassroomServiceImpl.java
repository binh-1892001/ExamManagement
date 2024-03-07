package trainingmanagement.service.Classroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.ClassroomRequest;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Enum.EStatusClass;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.ClassroomRepository;

import java.util.List;
@Service
public class IClassroomServiceImpl implements IClassroomService{
    @Autowired
    private ClassroomRepository classroomRepository;
    @Override
    public Page<Classroom> getAll(Pageable pageable) {
        return classroomRepository.findAll(pageable);
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
    public Classroom findById(Long id) {
        return classroomRepository.findById(id).orElse(null);
    }

    @Override
    public Classroom edit(ClassroomRequest classroomRequest, Long id) {
        Classroom classroom = Classroom.builder()
                .nameClass(classroomRequest.getNameClass())
                .status(classroomRequest.getEStatusClass())
                .build();
        classroom.setId(id);
        return classroomRepository.save(classroom);
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
