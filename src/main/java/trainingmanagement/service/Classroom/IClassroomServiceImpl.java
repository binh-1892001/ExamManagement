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
import java.util.Optional;

@Service
public class IClassroomServiceImpl implements IClassroomService{
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
    public Classroom edit(Classroom classroom, Long id) {
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
