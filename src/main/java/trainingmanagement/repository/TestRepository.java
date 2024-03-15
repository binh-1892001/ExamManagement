package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trainingmanagement.model.entity.Test;
import java.util.List;

public interface TestRepository extends JpaRepository<Test,Long> {
    List<Test> findByTestNameContainingIgnoreCase(String testName);
    boolean existsByTestName(String testName);
}