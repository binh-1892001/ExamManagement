/**
 * * Created by Tung.
 * * Fixed by NguyenHongQuan:
 * * - Create standard CRUD for TestService.
 * * - Add both Put and Patch method to edit Test entity.
 * * - Add both softDelete and hardDelete to delete Test entity.
 * @author: Phạm Văn Tùng.
 * @since: 14/3/2024.
 * */

package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.ATestRequest;
import trainingmanagement.model.dto.response.admin.ASubjectResponse;
import trainingmanagement.model.dto.response.admin.ATestResponse;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.ETestType;
import trainingmanagement.model.entity.Test;
import trainingmanagement.repository.TestRepository;
import trainingmanagement.service.TestService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;

    @Override
    public List<Test> getAllTestsToList() {
        return testRepository.findAll();
    }

    @Override
    public List<ATestResponse> getAllATestResponsesToList() {
        return getAllTestsToList().stream().map(this::entityAMap).toList();
    }

    @Override
    public Optional<Test> getTestById(Long testId) {
        return testRepository.findById(testId);
    }

    @Override
    public ATestResponse getATestResponseById(Long testId) throws CustomException{
        Optional<Test> optionalTest = getTestById(testId);
        if(optionalTest.isEmpty()) throw new CustomException("Test is not exists.");
        Test test = optionalTest.get();
        return entityAMap(test);
    }

    @Override
    public Test save(Test test) {
        return testRepository.save(test);
    }

    @Override
    public ATestResponse save(ATestRequest ATestRequest) {
        return entityAMap(testRepository.save(entityAMap(ATestRequest)));
    }

    @Override
    public ATestResponse putUpdateATest(Long testId, ATestRequest testRequest) {
        return null;
    }

    @Override
    public ATestResponse patchUpdateATest(Long testId, ATestRequest testRequest) throws CustomException {
        Optional<Test> updateTest = getTestById(testId);
        if(updateTest.isPresent()){
            Test test = updateTest.get();
            if(testRequest.getTestName() != null)
                test.setTestName(testRequest.getTestName());
            if(testRequest.getTestTime() != null)
                test.setTestTime(testRequest.getTestTime());
            ETestType testType = null;
            if(testRequest.getTestType() != null){
                testType = switch (testRequest.getTestType().toUpperCase()){
                    case "WRITENTEST" -> ETestType.WRITENTEST;
                    case "QUIZTEST" -> ETestType.QUIZTEST;
                    default -> null;
                };
                test.setTestType(testType);
            }
            if(testRequest.getResources() != null)
                test.setResources(testRequest.getResources());
            EActiveStatus status = null;
            if(testRequest.getStatus() != null){
                status = switch (testRequest.getStatus().toUpperCase()){
                    case "ACTIVE" -> EActiveStatus.ACTIVE;
                    case "INACTIVE" -> EActiveStatus.INACTIVE;
                    default -> null;
                };
                test.setStatus(status);
            }
            return entityAMap(testRepository.save(test));
        }
        throw new CustomException("Test is not exists.");
    }

    @Override
    public void softDeleteByTestId(Long testId) throws CustomException {
        Optional<Test> deleteTest = getTestById(testId);
        // ? Exception cần tìm thấy thì mới có thể xoá mềm.
        if(deleteTest.isEmpty()) throw new CustomException(("Test is not exists to delete."));
        Test test = deleteTest.get();
        test.setStatus(EActiveStatus.INACTIVE);
        testRepository.save(test);
    }

    @Override
    public void hardDeleteByTestId(Long testId) throws CustomException {
        // ? Exception cần tìm thấy thì mới có thể xoá cứng.
        if(!testRepository.existsById(testId)) throw new CustomException("Test is not exists to delete.");
        testRepository.deleteById(testId);
    }

    @Override
    public List<ATestResponse> findAllTestsByTestNameToList (String testName) {
        return testRepository.findByTestNameContainingIgnoreCase(testName)
                .stream().map(this::entityAMap).toList();
    }
    //find by examId
    @Override
    public List<ATestResponse> getAllByExamId(Long examId) {
        List<Test> tests = testRepository.getAllByExamId (examId);
        return tests.stream().map(this::entityAMap).toList();
    }

    @Override
    public List<ATestResponse> getAllByTestType(ETestType testType) {
        List<Test> tests = testRepository.getAllByTestType(testType);
        return tests.stream().map(this::entityAMap).toList();
    }

    @Override
    public List<ATestResponse> getAllByCreatedDate(LocalDate createdDate) {
        List<Test> tests = testRepository.getAllByCreatedDate(createdDate);
        return tests.stream().map(this::entityAMap).toList();
    }

    @Override
    public List<ATestResponse> getAllFromDateToDate(String dateStart, String dateEnd) {
        List<Test> tests = testRepository.getAllFromDateToDate(dateStart,dateEnd);
        return tests.stream().map(this::entityAMap).toList();
    }


    //    *********************************************entityMap*********************************************
    @Override
    public Test entityAMap(ATestRequest testRequest) {
        ETestType testType = null;
        if(testRequest.getTestType() != null)
            testType = switch (testRequest.getTestType().toUpperCase()){
                case "WRITENTEST" -> ETestType.WRITENTEST;
                case "QUIZTEST" -> ETestType.QUIZTEST;
                default -> null;
            };
        EActiveStatus status = null;
        if(testRequest.getStatus() != null)
            status = switch (testRequest.getStatus().toUpperCase()){
                case "ACTIVE" -> EActiveStatus.ACTIVE;
                case "INACTIVE" -> EActiveStatus.INACTIVE;
                default -> null;
            };
        return Test.builder()
            .testName(testRequest.getTestName())
            .testTime(testRequest.getTestTime())
            .testType(testType)
            .resources(testRequest.getResources())
            .status(status)
            .build();
    }

    @Override
    public ATestResponse entityAMap(Test test) {
        return ATestResponse.builder()
            .testId(test.getId())
            .testName(test.getTestName())
            .testTime(test.getTestTime())
            .testType(test.getTestType())
            .resources(test.getResources())
            .createdDate(test.getCreatedDate())
            .modifyDate(test.getModifyDate())
            .createdBy(test.getCreateBy())
            .modifyBy(test.getModifyBy())
            .status(test.getStatus())
            .build();
    }


}
