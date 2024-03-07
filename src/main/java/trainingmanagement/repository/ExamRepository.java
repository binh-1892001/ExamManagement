package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Exam;

import java.sql.Date;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Query("SELECT ex from Exam ex WHERE ex.examName like %?1% Or ex.createdDate = :createDate")
    List<Exam> findByNameOrDateTime(String examName, Date createDate);
    boolean existsByExamName(String examName);
}
