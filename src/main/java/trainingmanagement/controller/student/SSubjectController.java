package trainingmanagement.controller.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.SubjectService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/student/subject")
public class SSubjectController {
    private final SubjectService subjectService;

    @GetMapping("/allSubject")
    public ResponseEntity<?> allSubject(){
        List<Subject> subjects = subjectService.getAllSubjectByClassIdAndUserId();
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        subjects
                ), HttpStatus.OK);
    }
}
