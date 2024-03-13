package trainingmanagement.controller.permitAll;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainingmanagement.model.dto.Wrapper.ResponseWrapper;
import trainingmanagement.model.dto.request.admin.LoginRequest;
import trainingmanagement.model.dto.request.admin.RegisterRequest;
import trainingmanagement.model.entity.Enum.EHttpStatus;
import trainingmanagement.service.User.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth/")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> handleLogin(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                userService.handleLogin(loginRequest)
            ), HttpStatus.OK);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> handleRegister(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                userService.addUser(registerRequest)
            ), HttpStatus.CREATED);
    }
}
