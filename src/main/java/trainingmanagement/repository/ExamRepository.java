package trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.entity.Exam;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Page<Exam> getAllByStatus(EActiveStatus status, Pageable pageable);
    Page<Exam> searchByExamNameContainingIgnoreCase(Pageable pageable ,String examName);
    Page<Exam> findByCreatedDate(LocalDate createDate,Pageable pageable);
    Boolean existsByExamName(String examName);
    Page<Exam> findAll(Pageable pageable);
    Optional<Exam> findByIdAndStatus(Long examId, EActiveStatus status);
    //* find all exam from date to date
    @Query("select e from Exam e where e.createBy between :dateStart and :dateEnd")
    Page<Exam> getAllFromDateToDate(String dateStart, String dateEnd, Pageable pageable);
    //*Find All Exam By SubjectId
    @Query("SELECT e FROM Exam e WHERE e.subject.id=:subjectId")
    Page<Exam> getAllBySubjectId(Long subjectId, Pageable pageable);
    Exam findBySubject(Subject subject);
}
