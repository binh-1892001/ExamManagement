package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trainingmanagement.model.entity.Test;

import java.sql.Date;
import java.util.List;

public interface TestRepository extends JpaRepository<Test,Long> {
    List<Test> findByTestName(String testName);
//    List<Test> findByCreatedDate(Date createDate);
    boolean existsByTestName(String testName);
}
