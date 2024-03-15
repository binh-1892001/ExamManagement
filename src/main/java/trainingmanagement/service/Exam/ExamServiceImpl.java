package trainingmanagement.service.Exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.ExamRequest;
import trainingmanagement.model.dto.response.admin.ExamResponse;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Exam;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.ExamRepository;
import trainingmanagement.service.Subject.SubjectService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final SubjectService subjectService;
    @Override
    public List<Exam> getAllToList() {
        return examRepository.findAll();
    }
    @Override
    public List<ExamResponse> getAllExamResponsesToList() {
        return getAllToList().stream().map(this::entityMap).toList();
    }
    @Override
    public Optional<Exam> getById(Long examId) {
        return examRepository.findById(examId);
    }
    @Override
    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }
    @Override
    public Exam save(ExamRequest examRequest) {
        return examRepository.save(entityMap(examRequest));
    }
    @Override
    public Exam patchUpdateExam(Long examId, ExamRequest examRequest) throws CustomException {
        Optional<Exam> updateExam = examRepository.findById(examId);
        if(updateExam.isPresent()){
            Exam exam = updateExam.get();
            Optional<Subject> subject = subjectService.getById(examRequest.getSubjectId());
            if(subject.isEmpty()) throw new CustomException("Subject is not exists.");
            if(examRequest.getExamName() != null) exam.setExamName(examRequest.getExamName());
            if(examRequest.getStatus() != null) {
                EActiveStatus activeStatus = switch (examRequest.getStatus().toUpperCase()) {
                    case "INACTIVE" -> EActiveStatus.ACTIVE;
                    case "ACTIVE" -> EActiveStatus.INACTIVE;
                    default -> null;
                };
                exam.setStatus(activeStatus);
            }
            if(examRequest.getSubjectId() != null) exam.setSubject(subject.get());
            return examRepository.save(exam);
        }
        throw new CustomException("Exam is not exists to update.");
    }
    @Override
    public void deleteById(Long examId) {
        examRepository.deleteById (examId);
    }
    @Override
    public List<ExamResponse> searchByExamName(String examName) {
        return examRepository.findByExamName(examName).stream().map(this::entityMap).toList();
    }

    @Override
    public Exam entityMap(ExamRequest examRequest) {
        EActiveStatus activeStatus = switch (examRequest.getStatus().toUpperCase()) {
            case "INACTIVE" -> EActiveStatus.ACTIVE;
            case "ACTIVE" -> EActiveStatus.INACTIVE;
            default -> null;
        };
        return Exam.builder()
            .examName(examRequest.getExamName())
            .status(activeStatus)
            .subject(subjectService.getById(examRequest.getSubjectId()).orElse(null))
            .build();
    }

    @Override
    public ExamResponse entityMap(Exam exam) {
        return ExamResponse.builder()
                .examId(exam.getId())
                .examName(exam.getExamName())
                .status(exam.getStatus().name())
                .subject(exam.getSubject())
                .build();
    }
}