package trainingmanagement.controller.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.request.admin.ATestRequest;
import trainingmanagement.model.dto.response.admin.ATestResponse;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.security.UserDetail.UserLoggedIn;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.TestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/teacher/tests")
public class TTestController {
    private final TestService testService;
    private final CommonService commonService;
    private final UserLoggedIn userLoggedIn;

    //* lấy danh sách test theo examId
    @GetMapping("/exam/{examId}")
    public ResponseEntity<?> getAllTestByExamIdToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "testName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable Long examId
    ) throws CustomException {
        try {
            Pageable pageable;
            if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<ATestResponse> testResponses = testService.getAllByExamIdAndTeacher(examId, userLoggedIn.getUserLoggedIn().getUsername(), pageable);
            if (testResponses.getContent().isEmpty()) throw new CustomException("Tests page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            testResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    // * Get test by test id.
    @GetMapping("/{testId}")
    public ResponseEntity<?> getTestById(@PathVariable("testId") Long testId) throws CustomException {
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        testService.getATestResponseById(testId)
                ), HttpStatus.OK);
    }

    // * Create a new test.
    @PostMapping
    public ResponseEntity<?> createTest(@RequestBody ATestRequest testRequest) {
        ATestResponse testCreate = testService.save(testRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        testCreate
                ), HttpStatus.CREATED);
    }

    // * patchUpdate an exists test.
    @PatchMapping("/{testId}")
    public ResponseEntity<?> patchUpdateTest(
            @PathVariable("testId") Long testId,
            @RequestBody ATestRequest ATestRequest) throws CustomException {
        ATestResponse testUpdate = testService.patchUpdateATest(testId, ATestRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        testUpdate
                ), HttpStatus.OK);
    }

    // * softDelete an exists test.
    @DeleteMapping("/{testId}")
    public ResponseEntity<?> softDeleteTestById(@PathVariable("testId") Long testId) throws CustomException {
        testService.softDeleteByTestId(testId);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Delete test successfully."
                ), HttpStatus.OK);
    }

    // * hardDelete an exists test.
    @DeleteMapping("delete/{testId}")
    public ResponseEntity<?> hardDeleteTestById(@PathVariable("testId") Long testId) throws CustomException {
        testService.hardDeleteByTestId(testId);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Delete test successfully."
                ), HttpStatus.OK);
    }

    // * Find test by testName.
    @GetMapping("/search")
    public ResponseEntity<?> findByTestName(
            @RequestParam(name = "testName") String testName,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "testName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "sortBy") String sortBy
    ) throws CustomException {
        try {
            Pageable pageable;
            if (sortBy.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<ATestResponse> testResponses = testService.getAllByTestNameAndTeacherName(testName, userLoggedIn.getUserLoggedIn().getUsername(), pageable);
            if (testResponses.getContent().isEmpty()) throw new CustomException("Tests page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            testResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }
}