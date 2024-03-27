package trainingmanagement.controller.teacher;

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
import trainingmanagement.model.dto.request.teacher.TResultRequest;
import trainingmanagement.model.dto.response.teacher.TResultResponse;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.entity.Result;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.security.UserDetail.UserLoggedIn;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.ResultService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/teacher/result")
@RequiredArgsConstructor
public class TResultController {
    private final ResultService resultService;
    private final UserLoggedIn userLoggedIn;
    private final CommonService commonService;

    @GetMapping
    public ResponseEntity<?> getAllResultToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "sortBy") String sortBy
    ) throws CustomException {
        Pageable pageable;
        if (sortBy.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<TResultResponse> resultResponses = resultService.getAllResultResponsesToList(userLoggedIn.getUserLoggedIn());
            Page<?> result = commonService.convertListToPages(pageable, resultResponses);
            if (!result.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                result.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Results page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Results page is out of range.");
        }
    }

    @GetMapping("/{resultId}")
    public ResponseEntity<?> getResultById(@PathVariable("resultId") Long resultId) throws CustomException {
        Optional<Result> result = resultService.getById(resultId);
        if(result.isPresent())
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            result.get()
                    ), HttpStatus.OK);
        throw new CustomException("Result is not exists.");
    }

    @PostMapping
    public ResponseEntity<?> createNewResult(@RequestBody @Valid TResultRequest tResultRequest) throws CustomException {
        Result result = resultService.save(tResultRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        result
                ), HttpStatus.CREATED);
    }
    // * Patch update result.
    @PatchMapping("/{resultId}")
    public ResponseEntity<?> patchUpdateResult(
            @PathVariable("resultId") Long resultId,
            @RequestBody TResultRequest tResultRequest) throws CustomException {
        Result result = resultService.patchUpdateResult (resultId, tResultRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        result
                ), HttpStatus.OK);
    }
    // * softDelete result by id.
    @DeleteMapping("{resultId}")
    public ResponseEntity<?> softDeleteResultById(@PathVariable("resultId") Long resultId) throws CustomException {
        resultService.softDeleteById(resultId);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Delete result successfully."
                ), HttpStatus.OK);
    }
    // * hardDelete result by id.
    @DeleteMapping("/delete/{resultId}")
    public ResponseEntity<?> hardDeleteResultById(@PathVariable("resultId") Long resultId) throws CustomException {
        Optional<Result> deleteResult = resultService.getById(resultId);
        if(deleteResult.isPresent()){
            resultService.hardDeleteById(resultId);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            "Delete result successfully."
                    ), HttpStatus.OK);
        }
        // ? Xử lý Exception cần tìm được result theo id trước khi xoá trong Controller.
        throw new CustomException("result is not exists.");
    }
    // * Search all results By resultName to pages.
    @GetMapping("/search")
    public ResponseEntity<?> findByStudentName(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String sortBy
    ) throws CustomException {
        Pageable pageable;
        if (sortBy.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<TResultResponse> resultResponses = resultService.searchByStudentFullName(keyword);
            Page<?> results = commonService.convertListToPages(pageable, resultResponses);
            if (!results.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                results.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("results page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("results page is out of range.");
        }
    }
}
