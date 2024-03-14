package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Subject;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findBySubjectNameContainingIgnoreCase(String subjectName);
    @Query(value = "select s.* from subject s join class_subject cs on s.id=cs.subject_id where cs.class_id=:classId",nativeQuery = true)
    List<Subject> getAllByClassId(Long classId);
    List<Subject> getAllByStatus(EActiveStatus status);
    Optional<Subject> findByIdAndStatus(Long subjectId, EActiveStatus status);
}
