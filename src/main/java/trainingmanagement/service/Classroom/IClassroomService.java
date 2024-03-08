package trainingmanagement.service.Classroom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.model.dto.ClassroomRequest;
import trainingmanagement.model.dto.SubjectRequest;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Subject;

import java.util.List;
import java.util.Optional;

public interface IClassroomService {
    Page<Classroom> getAll(Pageable pageable);
    Optional<Classroom> getById(Long classroomId);
    Classroom save(ClassroomRequest classroomRequest);
    Classroom edit(Classroom classroom, Long id);
    void delete(Long id);
    List<Classroom> searchByName(String name);
}
