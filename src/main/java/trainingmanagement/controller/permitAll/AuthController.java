package trainingmanagement.controller.permitAll;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.auth.LoginRequest;
import trainingmanagement.model.dto.auth.RegisterRequest;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> handleLogin(@RequestBody @Valid LoginRequest loginRequest) throws CustomException{
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                userService.handleLogin(loginRequest)
            ), HttpStatus.OK);
    }

}
