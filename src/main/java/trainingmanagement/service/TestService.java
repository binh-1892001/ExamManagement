/**
 * * Created by Tung.
 * * Fixed by NguyenHongQuan:
 * * - Create standard CRUD for TestService.
 * * - Add both Put and Patch method to edit Test entity.
 * * - Add both softDelete and hardDelete to delete Test entity.
 * @author: Phạm Văn Tùng.
 * @since: 14/3/2024.
 * */

package trainingmanagement.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.ATestRequest;
import trainingmanagement.model.dto.response.admin.ATestResponse;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.ETestType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TestService {
    Page<Test> getAllTestsToList(Pageable pageable);
    Page<ATestResponse> getAllATestResponsesToList(Pageable pageable);
    Optional<Test> getTestById(Long testId);
    ATestResponse getATestResponseById(Long testId) throws CustomException;
    Page<ATestResponse> findAllTestsByTestNameToList(String testName, Pageable pageable);
    ATestResponse putUpdateATest(Long testId, ATestRequest testRequest);
    ATestResponse patchUpdateATest(Long testId, ATestRequest testRequest) throws CustomException;
    Test save(Test test);
    ATestResponse save(ATestRequest ATestRequest);
    void softDeleteByTestId(Long testId) throws CustomException;
    void hardDeleteByTestId(Long testId) throws CustomException;
    Test entityAMap(ATestRequest testRequest);
    ATestResponse entityAMap(Test test);
    //find by subjectId
    Page<ATestResponse> getAllByExamId(Long examId, Pageable pageable);
    Page<ATestResponse> getAllByExamIdAndTeacher(Long examId, String name, Pageable pageable);
    Page<ATestResponse> getAllByTestNameAndTeacherName(String testName, String name, Pageable pageable);
    //* find by TestType
    Page<ATestResponse> getAllByTestType(ETestType testType, Pageable pageable);
    //* find by created date
    Page<ATestResponse> getAllByCreatedDate(LocalDate createdDate, Pageable pageable);
    //* find by from date to date
    Page<ATestResponse> getAllFromDateToDate(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);
    List<Test> getAllTestByExamOfStudent();
}