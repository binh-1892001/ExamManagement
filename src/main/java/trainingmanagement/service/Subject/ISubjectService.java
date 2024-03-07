package trainingmanagement.service.Subject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.model.dto.SubjectRequest;
import trainingmanagement.model.entity.Subject;

import java.util.List;

public interface ISubjectService {
    Page<Subject> getAll(Pageable pageable);
    Subject add(SubjectRequest subjectRequest);
    Subject edit(SubjectRequest subjectRequest, Long id);
    Subject findById(Long id);
    List<Subject> getByName(String name);
    void delete(Long id);
}
