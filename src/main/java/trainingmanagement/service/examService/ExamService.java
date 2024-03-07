package trainingmanagement.service.examService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.model.dto.requestEntity.ExamRequest;
import trainingmanagement.model.entity.Exam;

import java.sql.Date;
import java.util.List;

public interface ExamService {
    Page<Exam> getAll(Pageable pageable);
    Exam findById(Long id);

    Exam add(ExamRequest examRequest);
    Exam edit(ExamRequest examRequest, Long id);

    void delete(Long id);
    List<Exam> getByNameOrDateTime(String examName, Date createDate);
}
