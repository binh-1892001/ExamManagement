package trainingmanagement.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.time.DateSearch;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.request.admin.ATestRequest;
import trainingmanagement.model.dto.response.admin.ATestResponse;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.enums.ETestType;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.TestService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/tests")
public class ATestController {
    private final TestService testService;
    private final CommonService commonService;
    // * Get all tests to pages.
    @GetMapping
    public ResponseEntity<?> getAllATestsResponsesToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "testName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "sortBy") String sortBy
    ) throws CustomException {
        Pageable pageable;
        if (sortBy.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<ATestResponse> testResponses = testService.getAllATestResponsesToList();
            Page<?> tests = commonService.convertListToPages(pageable, testResponses);
            if (!tests.isEmpty()) {
                return new ResponseEntity<>(
                    new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        tests.getContent()
                    ), HttpStatus.OK);
            }
            throw new CustomException("Tests page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Tests page is out of range.");
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
            @RequestBody ATestRequest ATestRequest) throws CustomException{
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
    public ResponseEntity<?> softDeleteTestById(@PathVariable("testId") Long testId) throws CustomException{
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
    public ResponseEntity<?> hardDeleteTestById(@PathVariable("testId") Long testId) throws CustomException{
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
        @RequestParam(name = "testName") String keyword,
        @RequestParam(defaultValue = "5", name = "limit") int limit,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "testName", name = "sort") String sort,
        @RequestParam(defaultValue = "asc", name = "sortBy") String sortBy
    ) throws CustomException {
        Pageable pageable;
        if (sortBy.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<ATestResponse> testResponses = testService.findAllTestsByTestNameToList(keyword);
            Page<?> tests = commonService.convertListToPages(pageable, testResponses);
            if (!tests.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                tests.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Tests page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Tests page is out of range.");
        }
    }

    //* lấy danh sách test theo examId
    @GetMapping("/exam/{examId}")
    public ResponseEntity<?> getAllTestByExamIdToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "testName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable Long examId
    ) throws CustomException{
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<ATestResponse> testResponses = testService.getAllByExamId (examId);
            Page<?> tests = commonService.convertListToPages(pageable, testResponses);
            if (!tests.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                tests.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Tests page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Tests page is out of range.");
        }
    }

    //* lấy danh sách test kiểu bài test
    @GetMapping("/{typeTest}")
    public ResponseEntity<?> getAllTestByTypeTest(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "testName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable String typeTest
    ) throws CustomException{
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<ATestResponse> testResponses = null;
            if (typeTest.equalsIgnoreCase(String.valueOf(ETestType.QUIZTEST))){
                testResponses = testService.getAllByTestType(ETestType.QUIZTEST);
            }else if(typeTest.equalsIgnoreCase(String.valueOf(ETestType.WRITENTEST))){
                testResponses = testService.getAllByTestType(ETestType.WRITENTEST);
            }
            Page<?> tests = commonService.convertListToPages(pageable, testResponses);
            if (!tests.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                tests.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Tests page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Tests page is out of range.");
        }
    }

    //* lấy danh sách test ngày tháng tạo
    @GetMapping("/createdDateTest")
    public ResponseEntity<?> getAllTestByCreatedDate(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "testName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestBody DateSearch dateSearch
    ) throws CustomException{
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            LocalDate date = LocalDate.parse(dateSearch.getCreateDate());
            List<ATestResponse> testResponses = testService.getAllByCreatedDate(date);
            Page<?> tests = commonService.convertListToPages(pageable, testResponses);
            if (!tests.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                tests.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Tests page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Tests page is out of range.");
        }
    }

    //* lấy danh sách test theo khoảng thời gian tạo
    @GetMapping("/fromDateToDate")
    public ResponseEntity<?> getAllTestFromDateToDate(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "testName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestBody DateSearch dateSearch
    ) throws CustomException{
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<ATestResponse> testResponses = testService.getAllFromDateToDate(dateSearch.getStartDate(), dateSearch.getEndDate());
            Page<?> tests = commonService.convertListToPages(pageable, testResponses);
            if (!tests.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                tests.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Tests page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Tests page is out of range.");
        }
    }
}