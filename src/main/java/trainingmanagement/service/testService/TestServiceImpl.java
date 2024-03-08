package trainingmanagement.service.testService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.requestEntity.TestRequest;
import trainingmanagement.model.entity.Exam;
import trainingmanagement.model.entity.Test;
import trainingmanagement.repository.TestRepository;
import trainingmanagement.service.examService.ExamService;

import java.sql.Date;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private ExamService examService;

    @Override
    public Page<Test> getAll(Pageable pageable) {
        return testRepository.findAll ( pageable );
    }

    @Override
    public Test findById(Long id) {
        return testRepository.findById ( id ).orElse ( null );
    }

    @Override
    public Test add(TestRequest testRequest) {
        if (testRepository.existsByNameTest ( testRequest.getNameTest () )) {
            throw new RuntimeException ( "De thi da ton tai" );
        }
        Exam exam = examService.findById ( testRequest.getExamId () );

        if (exam == null) {
            throw new RuntimeException ( "Không tồn tại bai thi!" );
        }
        Test test = Test.builder ()
                .nameTest ( testRequest.getNameTest () )
                .status ( true )
                .time ( testRequest.getTime () )
                .resources ( testRequest.getResources () )
                .typeTest ( testRequest.getTypeTest () )
                .exam ( exam )
                .build ();
        return testRepository.save ( test );
    }

    @Override
    public Test edit(TestRequest testRequest, Long id) {
        if (testRepository.existsByNameTest ( testRequest.getNameTest () )) {
            throw new RuntimeException ( "De thi da ton tai" );
        }
        Exam exam = examService.findById ( testRequest.getExamId () );

        if (exam == null) {
            throw new RuntimeException ( "Không tồn tại bai thi!" );
        }
        Test test = Test.builder ()
                .nameTest ( testRequest.getNameTest () )
                .status ( testRequest.getStatus () )
                .time ( testRequest.getTime () )
                .resources ( testRequest.getResources () )
                .typeTest ( testRequest.getTypeTest () )
                .exam ( exam )
                .build ();
        test.setId ( id );
        return testRepository.save ( test );
    }

    @Override
    public void delete(Long id) {
        testRepository.deleteById ( id );
    }

    @Override
    public List<Test> getByNameOrDateTime(String nameTest, Date createDate) {
        return testRepository.findByNameOrDateTime ( nameTest, createDate );
    }
}
