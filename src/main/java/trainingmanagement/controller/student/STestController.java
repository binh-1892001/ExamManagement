package trainingmanagement.controller.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.TestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/student/test")
public class STestController {
    private final TestService testService;

    @GetMapping("/allTest")
    public ResponseEntity<?> allSubject(){
        List<Test> tests = testService.getAllTestByExamOfStudent();
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        tests
                ), HttpStatus.OK);
    }
}