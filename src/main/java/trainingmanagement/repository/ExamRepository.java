package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Exam;

import java.sql.Date;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByExamName(String examName);
    List<Exam> findByCreatedDate(Date createDate);
    Boolean existsByExamName(String examName);
}
