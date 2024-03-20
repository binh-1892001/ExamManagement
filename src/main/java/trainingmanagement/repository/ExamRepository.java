package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.entity.Exam;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> getAllByStatus(EActiveStatus status);
    List<Exam> findByExamNameContainingIgnoreCase(String examName);
    List<Exam> findByCreatedDate(LocalDate createDate);
    Boolean existsByExamName(String examName);
    Optional<Exam> findByIdAndStatus(Long examId, EActiveStatus status);

    //Find All Exam By SubjectId
    @Query("select ex from Exam ex where ex.subject.id=:subject_id")
    List<Exam> getAllBySubjectId(Long subjectId);
}
