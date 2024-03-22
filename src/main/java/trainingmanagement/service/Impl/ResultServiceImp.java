package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.student.ListStudentChoice;
import trainingmanagement.model.dto.request.student.StudentChoice;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Result;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EOptionStatus;
import trainingmanagement.repository.ResultRepository;
import trainingmanagement.security.UserDetail.UserLogin;
import trainingmanagement.service.QuestionService;
import trainingmanagement.service.ResultService;
import trainingmanagement.service.TestService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ResultServiceImp implements ResultService {
    private final ResultRepository resultRepository;
    private final QuestionService questionService;
    private final TestService testService;
    private final UserLogin userLogin;

    //* học sinh gửi lên bài làm vaf chấm điểm
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
                    .student(userLogin.userLogin())
                    .test(testOptional.get())
                    .status(EActiveStatus.ACTIVE)
                    .mark(myMark)
                    .examTimes(1)
                    .build();
            return resultRepository.save(result);
        }
        throw new CustomException("Test is not exist!");
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