package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import trainingmanagement.model.entity.Test;

import java.sql.Date;
import java.util.List;

public interface TestRepository extends JpaRepository<Test,Long> {
    @Query("SELECT ts from Test ts WHERE ts.nameTest like %?1% Or ts.createdDate = :createDate")
    List<Test> findByNameOrDateTime(String nameTest, Date createDate);
    boolean existsByNameTest(String nameTest);
}
