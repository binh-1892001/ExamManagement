package trainingmanagement.controller.admin;

import jakarta.validation.Valid;
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
import trainingmanagement.model.dto.time.DateSearchCreatedDate;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.request.admin.ATestRequest;
import trainingmanagement.model.dto.response.admin.ATestResponse;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.enums.ETestType;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.TestService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;

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
        try {
        Pageable pageable;
        if (sortBy.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        Page<ATestResponse> testResponses = testService.getAllATestResponsesToList(pageable);
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
    public ResponseEntity<?> getTestById(@PathVariable("testId") String testId) throws CustomException {
        try {
            Long id = Long.parseLong(testId);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            testService.getATestResponseById(id)
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * Create a new test.
    @PostMapping
    public ResponseEntity<?> createTest(@RequestBody @Valid ATestRequest testRequest) {
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
            @PathVariable("testId") String testId,
            @RequestBody @Valid ATestRequest ATestRequest) throws CustomException {
        try {
            Long id = Long.parseLong(testId);
            ATestResponse testUpdate = testService.patchUpdateATest(id, ATestRequest);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            testUpdate
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * softDelete an exists test.
    @DeleteMapping("/{testId}")
    public ResponseEntity<?> softDeleteTestById(@PathVariable("testId") String testId) throws CustomException {
        try {
            Long id = Long.parseLong(testId);
            testService.softDeleteByTestId(id);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            "Delete test successfully."
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * hardDelete an exists test.
    @DeleteMapping("delete/{testId}")
    public ResponseEntity<?> hardDeleteTestById(@PathVariable("testId") String testId) throws CustomException {
        try {
            Long id = Long.parseLong(testId);
            testService.hardDeleteByTestId(id);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            "Delete test successfully."
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
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
        try {
        Pageable pageable;
        if (sortBy.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        Page<ATestResponse> testResponses = testService.findAllTestsByTestNameToList(keyword, pageable);
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


    //* lấy danh sách test theo examId
    @GetMapping("/exam/{examId}")
    public ResponseEntity<?> getAllTestByExamIdToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "testName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable String examId
    ) throws CustomException {
        try {
            Long idExam = Long.parseLong(examId);
            Pageable pageable;
            if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<ATestResponse> testResponses = testService.getAllByExamId(idExam, pageable);
            if (testResponses.getContent().isEmpty()) throw new CustomException("Tests page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            testResponses.getContent()
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        } catch (Exception exception) {
        throw new CustomException("An error occurred while processing the query!");
        }
    }

    //* lấy danh sách test kiểu bài test
    @GetMapping("/typeTest")
    public ResponseEntity<?> getAllTestByTypeTest(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "testName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestParam(defaultValue = "QUIZTEST", name = "typeTest") String typeTest
    ) throws CustomException {
        try {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        Page<ATestResponse> testResponses = null;
        if (typeTest.equalsIgnoreCase(String.valueOf(ETestType.QUIZTEST))) {
            testResponses = testService.getAllByTestType(ETestType.QUIZTEST, pageable);
        } else if (typeTest.equalsIgnoreCase(String.valueOf(ETestType.WRITENTEST))) {
            testResponses = testService.getAllByTestType(ETestType.WRITENTEST, pageable);
        } else {
            testResponses = Page.empty();
        }
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

    //* lấy danh sách test ngày tháng tạo
    @PostMapping ("/createdDateTest")
    public ResponseEntity<?> getAllTestByCreatedDate(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "testName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestBody @Valid DateSearchCreatedDate dateSearchCreatedDate
    ) throws CustomException {
        try {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        LocalDate date = LocalDate.parse(dateSearchCreatedDate.getCreateDate());
        Page<ATestResponse> testResponses = testService.getAllByCreatedDate(date, pageable);
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


    //* lấy danh sách test theo khoảng thời gian tạo
    @PostMapping("/fromDateToDate")
    public ResponseEntity<?> getAllTestFromDateToDate(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "testName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @RequestBody @Valid DateSearch dateSearch
    ) throws CustomException {
        try {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        LocalDate dateStart = LocalDate.parse(dateSearch.getStartDate());
        LocalDate dateEnd = LocalDate.parse(dateSearch.getEndDate());
        Page<ATestResponse> testResponses = testService.getAllFromDateToDate(dateStart, dateEnd, pageable);
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