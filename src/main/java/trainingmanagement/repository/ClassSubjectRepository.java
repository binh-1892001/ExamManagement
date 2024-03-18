package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.ClassSubject;

@Repository
public interface ClassSubjectRepository extends JpaRepository<ClassSubject,Long> {
}
