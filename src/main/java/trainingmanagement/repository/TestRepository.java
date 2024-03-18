package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trainingmanagement.model.entity.Test;

import java.sql.Date;
import java.util.List;

public interface TestRepository extends JpaRepository<Test,Long> {
    boolean existsByTestName(String testName);
    List<Test> findByTestNameContainingIgnoreCase(String testName);
}
