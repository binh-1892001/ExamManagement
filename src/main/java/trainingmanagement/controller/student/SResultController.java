package trainingmanagement.controller.student;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.student.ListStudentChoice;
import trainingmanagement.model.dto.response.student.SResultResponse;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.entity.Result;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.ResultService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/student")
public class SResultController {
    private final ResultService resultService;

    @PostMapping("/doTest/{testId}")
    public ResponseEntity<?> doTest(@RequestBody @Valid ListStudentChoice listStudentChoice, @PathVariable String testId) throws CustomException {
        try {
            Long idTest = Long.parseLong(testId);
            Result result = resultService.checkAndResultTest(listStudentChoice, idTest);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            resultService.entitySMap(result)
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    @GetMapping("/result/checkHistory")
    public ResponseEntity<?> checkHistory() {
        List<SResultResponse> results = resultService.displayResultsStudent();
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        results
                ), HttpStatus.OK);
    }

}
