package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.student.ListStudentChoice;
import trainingmanagement.model.dto.request.student.StudentChoice;
import trainingmanagement.model.dto.request.teacher.TResultRequest;
import trainingmanagement.model.dto.response.teacher.TResultResponse;
import trainingmanagement.model.entity.*;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EOptionStatus;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.repository.ResultRepository;
import trainingmanagement.security.UserDetail.UserLoggedIn;
import trainingmanagement.service.QuestionService;
import trainingmanagement.service.ResultService;
import trainingmanagement.service.TestService;
import trainingmanagement.service.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ResultServiceImp implements ResultService {
    private final ResultRepository resultRepository;
    private final QuestionService questionService;
    private final TestService testService;
    private final UserLoggedIn userLogin;
    private final UserService userService;

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
            Result result = Result.builder()
                    .user(userLogin.getUserLoggedIn())
                    .test(testOptional.get())
                    .status(EActiveStatus.ACTIVE)
                    .mark(myMark)
                    .examTimes(1)
                    .build();
            return resultRepository.save(result);
        }
        throw new CustomException("Test is not exist!");
    }

    @Override
    public List<Result> getAllByStudent() {
        return resultRepository.getAllByUser(userLogin.getUserLoggedIn());
    }

    @Override
    public List<Result> getAllToList(User teacher) {
        return resultRepository.findAllByTeacher(teacher);
    }

    @Override
    public List<TResultResponse> getAllResultResponsesToList(User teacher) {
        return getAllToList(teacher).stream().map(this::entityTMap).toList();
    }

    @Override
    public Optional<Result> getById(Long id) {
        return resultRepository.findByIdAndTeacher(id, userLogin.getUserLoggedIn());
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
        resultRepository.deleteByIdAndTeacher(id,userLogin.getUserLoggedIn());
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
    public List<TResultResponse> searchByStudentFullName(String fullName) {
        return resultRepository.findByStudentFullName(fullName, userLogin.getUserLoggedIn()).stream().map(this::entityTMap).toList();
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
                .teacher(userLogin.getUserLoggedIn())
                .test(testService.getTestById(tResultRequest.getTestId()).orElse(null))
                .status(activeStatus)
                .mark(tResultRequest.getMark())
                .examTimes(tResultRequest.getExamTimes())
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
