/**
 * * Created by PhamVanTung.
 * * Fixed by NguyenHongQuan:
 * * - Rename + fix findByTestNameContainingIgnoreCase method.
 * @author: Phạm Văn Tùng.
 * @since: 15/3/2024.
 * */

package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trainingmanagement.model.entity.Test;
import java.util.List;

public interface TestRepository extends JpaRepository<Test,Long> {
    boolean existsByTestName(String testName);
    List<Test> findByTestNameContainingIgnoreCase(String testName);
}