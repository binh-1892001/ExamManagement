package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Subject;

import java.util.List;

@Repository
public interface ClassroomRepository  extends JpaRepository<Classroom,Long> {
    List<Classroom> findByNameClass(String name);
}
