package trainingmanagement.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.enums.EActiveStatus;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository  extends JpaRepository<Classroom, Long> {
    boolean existsById(@NonNull Long classId);
    Optional<Classroom> findByClassName(String className);
    List<Classroom> findAll();
    Page<Classroom> findAll(Pageable pageable);
    List<Classroom> getAllByStatus(EActiveStatus status);
    List<Classroom> findByClassNameContainingIgnoreCase(String className);
    Page<Classroom> searchByClassNameContainingIgnoreCase(Pageable pageable, String className);
}