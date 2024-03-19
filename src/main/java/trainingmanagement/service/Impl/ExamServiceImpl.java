package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AExamRequest;
import trainingmanagement.model.dto.response.admin.AExamResponse;
import trainingmanagement.model.dto.response.teacher.TExamResponse;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.entity.Exam;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.repository.ExamRepository;
import trainingmanagement.service.ExamService;
import trainingmanagement.service.SubjectService;
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
    public List<AExamResponse> getAllExamResponsesToList() {
        return getAllToList().stream().map(this::entityAMap).toList();
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
    public Exam save(AExamRequest AExamRequest) {
        return examRepository.save(entityAMap(AExamRequest));
    }
    @Override
    public Exam patchUpdateExam(Long examId, AExamRequest AExamRequest) throws CustomException {
        Optional<Exam> updateExam = getById(examId);
        if(updateExam.isPresent()){
            Exam exam = updateExam.get();
            Optional<Subject> subject = subjectService.getById(AExamRequest.getSubjectId());
            if(subject.isEmpty()) throw new CustomException("Subject is not exists.");
            if(AExamRequest.getExamName() != null) exam.setExamName(AExamRequest.getExamName());
            if(AExamRequest.getStatus() != null) {
                EActiveStatus activeStatus = switch (AExamRequest.getStatus().toUpperCase()) {
                    case "INACTIVE" -> EActiveStatus.ACTIVE;
                    case "ACTIVE" -> EActiveStatus.INACTIVE;
                    default -> null;
                };
                exam.setStatus(activeStatus);
            }
            if(AExamRequest.getSubjectId() != null) exam.setSubject(subject.get());
            return examRepository.save(exam);
        }
        throw new CustomException("Exam is not exists to update.");
    }
    @Override
    public void deleteById(Long examId) {
        examRepository.deleteById (examId);
    }
    @Override
    public List<AExamResponse> searchByExamName(String examName) {
        return examRepository.findByExamName(examName).stream().map(this::entityAMap).toList();
    }
    @Override
    public Exam entityAMap(AExamRequest AExamRequest) {
        EActiveStatus activeStatus = switch (AExamRequest.getStatus().toUpperCase()) {
            case "INACTIVE" -> EActiveStatus.INACTIVE;
            case "ACTIVE" -> EActiveStatus.ACTIVE;
            default -> null;
        };
        return Exam.builder()
            .examName(AExamRequest.getExamName())
            .status(activeStatus)
            .subject(subjectService.getById(AExamRequest.getSubjectId()).orElse(null))
            .build();
    }
    @Override
    public AExamResponse entityAMap(Exam exam) {
        return AExamResponse.builder()
                .examId(exam.getId())
                .examName(exam.getExamName())
                .status(exam.getStatus())
                .subject(exam.getSubject())
                .createdDate(exam.getCreatedDate())
                .build();
    }
    @Override
    public TExamResponse entityTMap(Exam exam) {
        return TExamResponse.builder()
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
    public List<TExamResponse> getAllExamResponsesToListWithActiveStatus() {
        return getAllExamsToListWithActiveStatus().stream().map(this::entityTMap).toList();
    }
    // Lấy ra Exam theo id với trang thái Active(Teacher)
    @Override
    public Optional<Exam> getExamByIdWithActiveStatus(Long examId) {
        return examRepository.findByIdAndStatus(examId, EActiveStatus.ACTIVE);
    }
    @Override
    public Optional<TExamResponse> getExamResponsesByIdWithActiveStatus(Long examId) {
        Optional<Exam> optionalExam = getExamByIdWithActiveStatus(examId);
        if (optionalExam.isPresent()){
            Exam exam = optionalExam.get();
            return Optional.ofNullable(entityTMap(exam));
        }
        return Optional.empty();
    }
    //Lấy danh sách Exam theo ngày tạo
    @Override
    public List<AExamResponse> getAllExamByCreatedDate(LocalDate date) {
        return examRepository.findByCreatedDate(date).stream().map(this::entityAMap).toList();
    }
}