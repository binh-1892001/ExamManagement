package trainingmanagement.service.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.SubjectRequest;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.SubjectRepository;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService{
    @Autowired
    private SubjectRepository subjectRepository;
    @Override
    public Page<Subject> getAll(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    @Override
    public Subject add(SubjectRequest subjectRequest) {
        Subject subject = Subject.builder()
                .nameSubject(subjectRequest.getNameSubject())
                .time(subjectRequest.getTime())
                .timeToStudy(subjectRequest.getTimeToStudy())
                .status(true)
                .build();
        return subjectRepository.save(subject);
    }

    @Override
    public Subject edit(SubjectRequest subjectRequest, Long id) {
        Subject subject = Subject.builder()
                .nameSubject(subjectRequest.getNameSubject())
                .time(subjectRequest.getTime())
                .timeToStudy(subjectRequest.getTimeToStudy())
                .status(subjectRequest.getStatus())
                .build();
        subject.setId(id);
        return subjectRepository.save(subject);
    }


    @Override
    public Subject findById(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }

    @Override
    public List<Subject> getByName(String name) {
        return subjectRepository.findByNameSubject(name);
    }

    @Override
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
}
