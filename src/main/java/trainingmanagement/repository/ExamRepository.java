package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Question;
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
    //* find all exam from date to date
    @Query(value = "select * from Exam where created_date between :dateStart and :dateEnd",nativeQuery = true)
    List<Exam> getAllFromDateToDate(String dateStart, String dateEnd);
    //Find All Exam By SubjectId
    @Query(value = "SELECT * FROM Exam e WHERE e.subject_id=:subjectId", nativeQuery = true)
    List<Exam> getAllBySubjectId(Long subjectId);
}
