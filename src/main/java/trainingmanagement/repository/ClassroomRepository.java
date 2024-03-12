package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository  extends JpaRepository<Classroom, Long> {
    List<Classroom> findByClassNameContainingIgnoreCase(String className);
    @Query(value = "select c.* from classroom c join user_class uc on c.id=uc.class_id where user_id=:userId",nativeQuery = true)
    Optional<Classroom> findByUserId(Long userId);

    
}