package trainingmanagement.service.Exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.admin.request.ExamRequest;
import trainingmanagement.model.dto.admin.response.ExamResponse;
import trainingmanagement.model.dto.teacher.response.ExamResponseTeacher;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Exam;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.ExamRepository;
import trainingmanagement.service.Subject.SubjectService;

import java.time.LocalDate;
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
                .createdDate(exam.getCreatedDate().toString())
                .build();
    }
    @Override
    public ExamResponseTeacher entityMapTeacher(Exam exam) {
        return ExamResponseTeacher.builder()
                .examId(exam.getId())
                .examName(exam.getExamName())
                .subject(exam.getSubject())
                .build();
    }
    //Lấy danh sách Exam với trạng thái Active (Teacher)
    @Override
    public List<Exam> getAllExamsToListWithActiveStatus() {
        return examRepository.getAllByStatus(EActiveStatus.ACTIVE);
    }

    @Override
    public List<ExamResponseTeacher> getAllExamResponsesToListWithActiveStatus() {
        return getAllExamsToListWithActiveStatus().stream().map(this::entityMapTeacher).toList();
    }
    // Lấy ra Exam theo id với trang thái Active(Teacher)
    @Override
    public Optional<Exam> getExamByIdWithActiveStatus(Long examId) {
        return examRepository.findByIdAndStatus(examId, EActiveStatus.ACTIVE);
    }

    @Override
    public Optional<ExamResponseTeacher> getExamResponsesByIdWithActiveStatus(Long examId) {
        Optional<Exam> optionalExam = getExamByIdWithActiveStatus(examId);
        if (optionalExam.isPresent()){
            Exam exam = optionalExam.get();
            return Optional.ofNullable(entityMapTeacher(exam));
        }
        return Optional.empty();
    }

    //Lấy danh sách Exam theo ngày tạo

    @Override
    public List<ExamResponse> getAllExamByCreatedDate(LocalDate date) {
        return examRepository.findByCreatedDate(date).stream().map(this::entityMap).toList();
    }
}