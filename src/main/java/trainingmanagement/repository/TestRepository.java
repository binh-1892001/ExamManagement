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
    @Query("select ts from Test ts where ts.exam.id=:exam_id")
    List<Test> getAllByExamId(Long examId);
}