package trainingmanagement.controller.Student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.Wrapper.ResponseWrapper;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Enum.EHttpStatus;
import trainingmanagement.model.entity.User;
import trainingmanagement.security.UserDetail.UserLogin;
import trainingmanagement.service.Classroom.ClassroomService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class SClassController {
    private final ClassroomService classroomService;
    private final UserLogin userLogin;

    @GetMapping("/class")
    public ResponseEntity<?> getInformationClass() throws CustomException {
        User user = userLogin.userLogin();
        Optional<Classroom> classroom = classroomService.findByUserId(user.getId());
        if (classroom.isEmpty())
            throw new CustomException("Class is not exists.");
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        classroom.get()
                ), HttpStatus.OK);
    }
}
