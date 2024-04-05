/**
 * * Created by PhamVanTung.
 * * Fixed by NguyenHongQuan:
 * * - Rename + fix findByTestNameContainingIgnoreCase method.
 * @author: Phạm Văn Tùng.
 * @since: 15/3/2024.
 * */

package trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import trainingmanagement.model.entity.Exam;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.ETestType;

import java.time.LocalDate;
import java.util.List;

public interface TestRepository extends JpaRepository<Test,Long> {
    boolean existsByTestName(String testName);
    Page<Test> findByTestNameContainingIgnoreCase(String testName, Pageable pageable);
    //Find All Test By ExamId
    @Query("select t from Test t where t.exam.id=:examId")
    Page<Test> getAllByExamId(Long examId, Pageable pageable);
    @Query("select t from Test t where t.exam.id=:examId and t.createBy =:name")
    Page<Test> getAllByExamIdAndTeacherName(Long examId, String name, Pageable pageable);
    @Query("select t from Test t where t.testName LIKE CONCAT('%', :testName, '%') and t.createBy =:name")
    Page<Test> getAllByTestNameAndTestName(String testName, String name, Pageable pageable);
    //* find all test from date to date
//    @Query(value = "select * from test where created_date between :dateStart and :dateEnd",nativeQuery = true)
    @Query("SELECT t FROM Test t WHERE t.createdDate between :dateStart and :dateEnd")
    Page<Test> getAllFromDateToDate(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);
    Page<Test> getAllByTestType(ETestType testType, Pageable pageable);
    Page<Test> getAllByCreatedDate(LocalDate createdDate, Pageable pageable);
    Test findByExam(Exam exam);

    //--------------------------------Page-------------------------------------------------
    Page<Test> findAll(Pageable pageable);
}