/**
 * * Created by PhamVanTung.
 * * Fixed by NguyenHongQuan:
 * * - Rename + fix findByTestNameContainingIgnoreCase method.
 * @author: Phạm Văn Tùng.
 * @since: 15/3/2024.
 * */

package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import trainingmanagement.model.entity.Exam;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.ETestType;

import java.time.LocalDate;
import java.util.List;

public interface TestRepository extends JpaRepository<Test,Long> {
    boolean existsByTestName(String testName);
    List<Test> findByTestNameContainingIgnoreCase(String testName);
    //Find All Test By ExamId
    @Query(value = "select * from Test t where t.exam_id=:examId", nativeQuery = true)
    List<Test> getAllByExamId(Long examId);
    //* find all test from date to date
    @Query(value = "select * from test where created_date between :dateStart and :dateEnd",nativeQuery = true)
    List<Test> getAllFromDateToDate(String dateStart, String dateEnd);
    List<Test> getAllByTestType(ETestType testType);
    List<Test> getAllByCreatedDate(LocalDate createdDate);
    Test findByExam(Exam exam);
}