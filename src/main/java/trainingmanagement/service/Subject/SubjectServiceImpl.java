package trainingmanagement.service.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingmanagement.model.base.AuditableEntity;
import trainingmanagement.model.dto.SubjectRequest;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Enum.EStatusClass;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService{
    @Autowired
    private SubjectRepository subjectRepository;
    @Override
    public Page<Subject> getAll(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    @Override
    public Subject save(SubjectRequest subjectRequest) {
        Subject subject = Subject.builder()
                .nameSubject(subjectRequest.getNameSubject())
                .time(subjectRequest.getTime())
                .timeToStudy(subjectRequest.getTimeToStudy())
                .status(true)
                .build();
        return subjectRepository.save(subject);
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject edit(SubjectRequest subjectRequest, Long id) {

        Subject subject = findById(id);
        subject.setNameSubject(subjectRequest.getNameSubject());
        subject.setStatus(subjectRequest.getStatus());
        subject.setTime(subjectRequest.getTime());
        subject.setTimeToStudy(subjectRequest.getTimeToStudy());

        return subjectRepository.save(subject);

//        if(updateSubject.isPresent()) {
//            Subject subject = updateSubject.get();
//            AuditableEntity auditableEntity = updateSubject.get();
//            if (auditableEntity.getCreatedDate() != null) {
//                auditableEntity.setCreatedDate(auditableEntity.getCreatedDate());
//            }
//            if (subjectRequest.getNameSubject() != null) {
//                subject.setNameSubject(subjectRequest.getNameSubject());
//            }
//            if (subjectRequest.getStatus() != null) {
//               subject.setStatus(subjectRequest.getStatus());
//            }
//            return save(subject);
//        }
//        return null;
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
