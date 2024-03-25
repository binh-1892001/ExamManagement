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
import trainingmanagement.model.entity.Test;
import java.util.List;

public interface TestRepository extends JpaRepository<Test,Long> {
    boolean existsByTestName(String testName);
    List<Test> findByTestNameContainingIgnoreCase(String testName);

    //Find All Test By ExamId
    @Query(value = "select * from Test t where t.exam_id=:examId", nativeQuery = true)
    List<Test> getAllByExamId(Long examId);
    @Query(value = "select t from Test t where t.exam.id=:examId and t.createBy =:name")
    List<Test> getAllByExamIdAndTeacherName(Long examId, String name);
    @Query(value = "select t from Test t where t.testName LIKE CONCAT('%', :testName, '%') and t.createBy =:name")
    List<Test> getAllByTestNameAndTestName(String testName, String name);

}