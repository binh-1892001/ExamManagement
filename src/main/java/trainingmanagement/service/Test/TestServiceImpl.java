package trainingmanagement.service.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.request.TestRequest;
import trainingmanagement.model.entity.Exam;
import trainingmanagement.model.entity.Test;
import trainingmanagement.repository.TestRepository;
import trainingmanagement.service.Exam.ExamService;

import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private ExamService examService;

    @Override
    public Page<Test> getAll(Pageable pageable) {
        return testRepository.findAll(pageable);
    }

    @Override
    public Test findById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    @Override
    public Test add(TestRequest testRequest) {
        if (testRepository.existsByNameTest(testRequest.getNameTest())) {
            throw new RuntimeException("De thi da ton tai");
        }
        Optional<Exam> exam = examService.getById(testRequest.getExamId());
        if (exam.isEmpty())
            throw new RuntimeException("Không tồn tại bai thi!");
        Test test = Test.builder()
                .nameTest(testRequest.getNameTest())
                .status(true)
                .time(testRequest.getTime())
                .resources(testRequest.getResources())
                .typeTest(testRequest.getTypeTest())
                .exam(exam.get())
                .build();
        return testRepository.save(test);
    }

    @Override
    public Test edit(TestRequest testRequest, Long id) {
        if (testRepository.existsByNameTest(testRequest.getNameTest())) {
            throw new RuntimeException("De thi da ton tai");
        }
        Optional<Exam> exam = examService.getById(testRequest.getExamId());
        if (exam.isEmpty())
            throw new RuntimeException("Không tồn tại bai thi!");
        Test test = Test.builder()
                .nameTest(testRequest.getNameTest())
                .status(testRequest.getStatus())
                .time(testRequest.getTime())
                .resources(testRequest.getResources())
                .typeTest(testRequest.getTypeTest())
                .exam(exam.get())
                .build();
        test.setId(id);
        return testRepository.save(test);
    }

    @Override
    public void delete(Long id) {
        testRepository.deleteById(id);
    }

    @Override
    public List<Test> getByNameTest(String nameTest) {
        return testRepository.findByNameTest(nameTest);
    }
}
