package trainingmanagement.service.testService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.model.dto.requestEntity.TestRequest;
import trainingmanagement.model.entity.Test;

import java.sql.Date;
import java.util.List;

public interface TestService {
    Page<Test> getAll(Pageable pageable);
    Test findById(Long id);

    Test add(TestRequest testRequest);
    Test edit(TestRequest testRequest, Long id);

    void delete(Long id);
    List<Test> getByNameTest(String nameTest);
}
