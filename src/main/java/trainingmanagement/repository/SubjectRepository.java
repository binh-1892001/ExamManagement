package trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.model.enums.EActiveStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Page<Subject> findBySubjectNameContainingIgnoreCase(String subjectName,Pageable pageable);
    @Query(value = "select s.* from subject s join class_subject cs on s.id=cs.subject_id where cs.class_id=:classId",nativeQuery = true)
    List<Subject> getAllByClassId(Long classId);
    Page<Subject> getAllByStatus(EActiveStatus status,Pageable pageable);
    Optional<Subject> findByIdAndStatus(Long subjectId, EActiveStatus status);
    Page<Subject> findAll(Pageable pageable);
}
