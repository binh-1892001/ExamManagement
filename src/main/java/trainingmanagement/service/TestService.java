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
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.ATestRequest;
import trainingmanagement.model.dto.response.admin.ATestResponse;
import trainingmanagement.model.entity.Test;
import java.util.List;
import java.util.Optional;

public interface TestService {
    List<Test> getAllTestsToList();
    List<ATestResponse> getAllATestResponsesToList();
    Optional<Test> getTestById(Long testId);
    ATestResponse getATestResponseById(Long testId) throws CustomException;
    List<ATestResponse> findAllTestsByTestNameToList(String testName);
    ATestResponse putUpdateATest(Long testId, ATestRequest testRequest);
    ATestResponse patchUpdateATest(Long testId, ATestRequest testRequest) throws CustomException;
    Test save(Test test);
    ATestResponse save(ATestRequest ATestRequest);
    void softDeleteByTestId(Long testId) throws CustomException;
    void hardDeleteByTestId(Long testId) throws CustomException;
    Test entityAMap(ATestRequest testRequest);
    ATestResponse entityAMap(Test test);
}