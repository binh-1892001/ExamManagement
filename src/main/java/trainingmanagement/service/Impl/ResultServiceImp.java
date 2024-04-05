package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.student.ListStudentChoice;
import trainingmanagement.model.dto.request.student.StudentChoice;
import trainingmanagement.model.dto.request.teacher.TResultRequest;
import trainingmanagement.model.dto.response.student.SResultResponse;
import trainingmanagement.model.dto.response.teacher.TResultResponse;
import trainingmanagement.model.entity.*;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EOptionStatus;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.repository.ResultRepository;
import trainingmanagement.security.UserDetail.UserLoggedIn;
import trainingmanagement.service.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ResultServiceImp implements ResultService {
    private final ResultRepository resultRepository;
    private final QuestionService questionService;
    private final TestService testService;
    private final UserLoggedIn userLoggedIn;
    private final UserService userService;
    private final ClassroomService classroomService;

    //* học sinh gửi lên bài làm và chấm điểm
    @Override
    public Result checkAndResultTest(ListStudentChoice listStudentChoice, Long testId) throws CustomException {
        Optional<Test> testOptional = testService.getTestById(testId);
        if (testOptional.isPresent()) {
            List<Question> questions = questionService.getAllQuestionByTest(testOptional.get());
            double markOfQuestion = (double) 10 / (questions.size());
            double myMark = 0;
            List<StudentChoice> studentChoices = listStudentChoice.getStudentChoices();
            for (Question question : questions) {
                for (StudentChoice studentChoice : studentChoices) {
                    if (Objects.equals(question.getId(), studentChoice.getIdQuestion())) {
                        List<Long> listIdOptionCorrect = new ArrayList<>();
                        for (Option option : question.getOptions()) {
                            if (option.getIsCorrect() == EOptionStatus.CORRECT) {
                                listIdOptionCorrect.add(option.getId());
                            }
                        }
                        if (compareLists(listIdOptionCorrect, studentChoice.getListIdOptionStudentChoice())) {
                            myMark += markOfQuestion;
                        }
                        break;
                    }
                }
            }
            List<Result> results = findAllByUserAndTest(userLoggedIn.getUserLoggedIn(),testOptional.get());
            int count = results.size();
            Result result = Result.builder()
                    .user(userLoggedIn.getUserLoggedIn())
                    .test(testOptional.get())
                    .status(EActiveStatus.ACTIVE)
                    .mark(myMark)
                    .examTimes(++count)
                    .build();
            return resultRepository.save(result);
        }
        throw new CustomException("Test is not exist!");
    }

    @Override
    public List<Result> getAllByStudent() {
        return resultRepository.getAllByUser(userLoggedIn.getUserLoggedIn());
    }

    @Override
    public List<SResultResponse> displayResultsStudent() {
        return getAllByStudent().stream().map(this::entitySMap).toList();
    }

    @Override
    public List<Result> findAllByUserAndTest(User user, Test test) {
        return resultRepository.findAllByUserAndTest(user,test);
    }

    @Override
    public List<Result> getAllToListByClassIdAndTeacher(Long classId) throws CustomException {
        List<Classroom> classrooms = classroomService.getAllByTeacher(userLoggedIn.getUserLoggedIn());
        for (Classroom classroom:classrooms){
            if (classId.equals(classroom.getId())) {
                return resultRepository.findAllByClassId(classId);
            }
        }
        throw new CustomException("Class is not exists or not in class of you");
    }

    @Override
    public List<TResultResponse> getAllResultResponsesToList(Long classId) throws CustomException {
        return getAllToListByClassIdAndTeacher(classId).stream().map(this::entityTMap).toList();
    }

    @Override
    public Optional<Result> getById(Long id) {
        return resultRepository.findByIdAndTeacher(id, userLoggedIn.getUserLoggedIn());
    }

    @Override
    public Result save(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public Result save(TResultRequest resultRequest) throws CustomException{
        Optional<User> student = userService.getUserById(resultRequest.getStudentId());
        if (student.isPresent()) {
            boolean isStudentRole = student.get().getRoles().stream()
                    .anyMatch(r -> ERoleName.ROLE_STUDENT.equals(r.getRoleName()));
            if (isStudentRole) {
                return resultRepository.save(entityTMap(resultRequest));
            }
        }
        throw new CustomException("Student does not exist!");
    }

    @Override
    public Result patchUpdateResult(Long resultId, TResultRequest tResultRequest) throws CustomException {
        Optional<Result> updateResult = resultRepository.findById(resultId);
        if(updateResult.isPresent()){
            Result result = updateResult.get();
            if(tResultRequest.getStudentId() != null) {
                Optional<User> student = userService.getUserById(tResultRequest.getStudentId());
                if(student.isEmpty()) throw new CustomException("Student is not exists.");
                result.setUser(student.get());
            }
            if(tResultRequest.getTestId() != null) {
                Optional<Test> test = testService.getTestById(tResultRequest.getTestId());
                if(test.isEmpty()) throw new CustomException("Test is not exists.");
                result.setTest(test.get());
            }
            if(tResultRequest.getStatus() != null) {
                EActiveStatus activeStatus = switch (tResultRequest.getStatus().toUpperCase()) {
                    case "INACTIVE" -> EActiveStatus.INACTIVE;
                    case "ACTIVE" -> EActiveStatus.ACTIVE;
                    default -> null;
                };
                result.setStatus(activeStatus);
            }
            if (tResultRequest.getExamTimes() != null) {
                result.setExamTimes(tResultRequest.getExamTimes());
            }
            if (tResultRequest.getMark() != null) {
                result.setMark(tResultRequest.getMark());
            }
            return resultRepository.save(result);
        }
        throw new CustomException("Result is not exists to update.");
    }

    @Override
    public void hardDeleteById(Long id) {
        resultRepository.deleteByIdAndTeacher(id,userLoggedIn.getUserLoggedIn());
    }

    @Override
    public void softDeleteById(Long id) throws CustomException{
        // ? Exception cần tìm thấy thì mới có thể xoá mềm.
        Optional<Result> deleteResult = getById(id);
        if(deleteResult.isEmpty())
            throw new CustomException("Result is not exists to delete.");
        Result result = deleteResult.get();
        result.setStatus(EActiveStatus.INACTIVE);
        resultRepository.save(result);
    }

    @Override
    public List<TResultResponse> searchByStudentFullName(String fullName,Long classId) throws CustomException {
        List<TResultResponse> resultResponses = getAllResultResponsesToList(classId);
        List<TResultResponse> tResultResponses = new ArrayList<>();
        for (TResultResponse tResultResponse:resultResponses){
            if (StringUtils.containsIgnoreCase(tResultResponse.getStudent().getFullName(), fullName)){
                tResultResponses.add(tResultResponse);
            }
        }
        return tResultResponses;
    }

    @Override
    public TResultResponse entityTMap(Result result) {
        return TResultResponse.builder()
                .student(result.getUser())
                .test(result.getTest())
                .mark(result.getMark())
                .build();
    }

    @Override
    public Result entityTMap(TResultRequest tResultRequest){
        EActiveStatus activeStatus = switch (tResultRequest.getStatus().toUpperCase()) {
            case "INACTIVE" -> EActiveStatus.INACTIVE;
            case "ACTIVE" -> EActiveStatus.ACTIVE;
            default -> null;
        };

        return Result.builder()
                .user(userService.getUserById(tResultRequest.getStudentId()).orElse(null))
                .teacher(userLoggedIn.getUserLoggedIn())
                .test(testService.getTestById(tResultRequest.getTestId()).orElse(null))
                .status(activeStatus)
                .mark(tResultRequest.getMark())
                .examTimes(tResultRequest.getExamTimes())
                .build();
    }

    @Override
    public SResultResponse entitySMap(Result result) {
        return SResultResponse.builder()
                .id(result.getId())
                .createdDate(result.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .userId(result.getUser().getId())
                .userName(result.getUser().getFullName())
                .testId(result.getTest().getId())
                .testName(result.getTest().getTestName())
                .status(result.getStatus().toString())
                .mark(result.getMark())
                .examTimes(result.getExamTimes())
                .build();
    }


    public boolean compareLists(List<Long> list1, List<Long> list2) {
        // Nếu độ dài của hai danh sách khác nhau, chúng không giống nhau
        if (list1.size() != list2.size()) {
            return false;
        }
        // Sắp xếp hai danh sách
        Collections.sort(list1);
        Collections.sort(list2);
        // So sánh từng phần tử của hai danh sách
        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false; // Nếu phần tử tại vị trí i không giống nhau, trả về false
            }
        }
        // Nếu tất cả các phần tử giống nhau, trả về true
        return true;
    }


}
