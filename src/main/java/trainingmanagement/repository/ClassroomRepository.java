package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Classroom;
import java.util.List;

@Repository
public interface ClassroomRepository  extends JpaRepository<Classroom, Long> {
    boolean existsById(Long classId);
    List<Classroom> findByClassNameContainingIgnoreCase(String className);
}