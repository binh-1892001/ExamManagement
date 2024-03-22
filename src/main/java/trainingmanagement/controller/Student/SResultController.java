package trainingmanagement.controller.Student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.student.ListStudentChoice;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.entity.Result;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.ResultService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/student/result")
public class SResultController {
    private final ResultService resultService;

    @PostMapping("/{testId}")
    public ResponseEntity<?> doTest(@RequestBody ListStudentChoice listStudentChoice, @PathVariable Long testId) throws CustomException {
        Result result = resultService.checkAndResultTest(listStudentChoice,testId);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        result
                ), HttpStatus.OK);
    }

}
