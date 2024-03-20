package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.enums.EActiveStatus;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository  extends JpaRepository<Classroom, Long> {
    boolean existsById(Long classId);
    List<Classroom> getAllByStatus(EActiveStatus status);
    List<Classroom> findByClassNameContainingIgnoreCase(String className);
}