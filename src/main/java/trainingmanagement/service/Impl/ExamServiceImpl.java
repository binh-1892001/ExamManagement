package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final SubjectService subjectService;
    @Override
    public Page<Exam> getAllToList(Pageable pageable) {
        return examRepository.findAll(pageable);
    }
    @Override
    public Page<AExamResponse> getAllExamResponsesToList(Pageable pageable) {
        return getAllToList(pageable).map(this::entityAMap);
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
    public Exam save(AExamRequest examRequest) {
        return examRepository.save(entityAMap(examRequest));
    }
    @Override
    public Exam patchUpdateExam(Long examId, AExamRequest examRequest) throws CustomException {
        Optional<Exam> updateExam = examRepository.findById(examId);
        if(updateExam.isPresent()){
            Exam exam = updateExam.get();
            if(examRequest.getExamName() != null) exam.setExamName(examRequest.getExamName());
            if(examRequest.getStatus() != null) {
                EActiveStatus activeStatus = switch (examRequest.getStatus().toUpperCase()) {
                    case "INACTIVE" -> EActiveStatus.INACTIVE;
                    case "ACTIVE" -> EActiveStatus.ACTIVE;
                    default -> null;
                };
                exam.setStatus(activeStatus);
            }
            if(examRequest.getSubjectId() != null) {
                Optional<Subject> subject = subjectService.getById(examRequest.getSubjectId());
                if(subject.isEmpty()) throw new CustomException("Subject is not exists.");
                exam.setSubject(subject.get());
            }
            return examRepository.save(exam);
        }
        throw new CustomException("Exam is not exists to update.");
    }
    @Override
    public void hardDeleteById(Long examId) {
        examRepository.deleteById (examId);
    }

    @Override
    public void softDeleteById(Long examId) throws CustomException {
        // ? Exception cần tìm thấy thì mới có thể xoá mềm.
        Optional<Exam> deleteExam = getById(examId);
        if(deleteExam.isEmpty())
            throw new CustomException("Exam is not exists to delete.");
        Exam exam = deleteExam.get();
        exam.setStatus(EActiveStatus.INACTIVE);
        examRepository.save(exam);
    }

    @Override
    public Page<AExamResponse> searchByExamName(String examName, Pageable pageable) {
        return examRepository.searchByExamNameContainingIgnoreCase(pageable,examName).map(this::entityAMap);
    }
    //Lấy danh sách Exam với trạng thái Active (Teacher)
    @Override
    public Page<Exam> getAllExamsToListWithActiveStatus(Pageable pageable) {
        return examRepository.getAllByStatus(EActiveStatus.ACTIVE, pageable);
    }
    @Override
    public Page<TExamResponse> getAllExamResponsesToListWithActiveStatus(Pageable pageable) {
        return getAllExamsToListWithActiveStatus(pageable).map(this::entityTMap);
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
    //*Lấy danh sách Exam theo ngày tạo
    @Override
    public Page<AExamResponse> getAllExamByCreatedDate(LocalDate date, Pageable pageable) {
        return examRepository.findByCreatedDate(date,pageable).map(this::entityAMap);
    }
    //*Lấy danh sách Exam theo khoảng thời gian tạo
    @Override
    public Page<AExamResponse> getAllExamFromDateToDate(String dateStart, String dateEnd,Pageable pageable) {
        return examRepository.getAllFromDateToDate(dateStart,dateEnd,pageable).map(this::entityAMap);
    }

    //find by subjectId
    @Override
    public Page<AExamResponse> getAllBySubjectId(Long subjectId, Pageable pageable) {
        return examRepository.getAllBySubjectId(subjectId, pageable).map(this::entityAMap);
    }

    @Override
    public List<Exam> getAllExamBySubjectOfStudent() {
        List<Subject> subjects = subjectService.getAllSubjectByClassIdAndUserId();
        List<Exam> exams = new ArrayList<>();
        for (Subject subject : subjects ){
            exams.add(examRepository.findBySubject(subject));
        }
        return exams;
    }

    @Override
    public Exam entityAMap(AExamRequest examRequest) {
        EActiveStatus activeStatus = switch (examRequest.getStatus().toUpperCase()) {
            case "INACTIVE" -> EActiveStatus.INACTIVE;
            case "ACTIVE" -> EActiveStatus.ACTIVE;
            default -> null;
        };
        return Exam.builder()
                .examName(examRequest.getExamName())
                .status(activeStatus)
                .subject(subjectService.getById(examRequest.getSubjectId()).orElse(null))
                .build();
    }
    @Override
    public AExamResponse entityAMap(Exam exam) {
        return AExamResponse.builder()
                .examId(exam.getId())
                .examName(exam.getExamName())
                .status(exam.getStatus())
                .subjectName(exam.getSubject().getSubjectName())
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
}