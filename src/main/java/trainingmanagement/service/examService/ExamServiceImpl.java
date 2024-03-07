package trainingmanagement.service.examService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.requestEntity.ExamRequest;
import trainingmanagement.model.entity.Exam;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.ExamRepository;

import java.sql.Date;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepository examRepository;

    @Override
    public Page<Exam> getAll(Pageable pageable) {
        return examRepository.findAll ( pageable );
    }

    @Override
    public Exam findById(Long id) {
        return examRepository.findById ( id ).orElse ( null );
    }

    @Override
    public Exam add(ExamRequest examRequest) {
        if (examRepository.existsByExamName (examRequest.getExamName ())){
            throw new RuntimeException("Bai thi da ton tai");
        }
        Subject subject = subjectService.findById(examRequest.getSubjectId ());

        if (subject == null) {
            throw new RuntimeException("Không tồn tại mon hoc!");
        }
        Exam exam = Exam.builder ()
                .examName ( examRequest.getExamName () )
                .status ( true )
                .subject ( subject )
                .build ();
        return examRepository.save ( exam );
    }

    @Override
    public Exam edit(ExamRequest examRequest, Long id) {
        if (examRepository.existsByExamName (examRequest.getExamName ())){
            throw new RuntimeException("Bai thi da ton tai");
        }
        Subject subject = subjectService.findById(examRequest.getSubjectId ());

        if (subject == null) {
            throw new RuntimeException("Không tồn tại mon hoc!");
        }
        Exam exam = Exam.builder ()
                .examName ( examRequest.getExamName () )
                .status (true)
                .subject ( subject )
                .build ();
        exam.setId ( id );
        return examRepository.save ( exam );
    }

    @Override
    public void delete(Long id) {
        examRepository.deleteById ( id );
    }

    @Override
    public List<Exam> getByNameOrDateTime(String examName, Date createDate) {
        return examRepository.findByNameOrDateTime ( examName,createDate );
    }
}
