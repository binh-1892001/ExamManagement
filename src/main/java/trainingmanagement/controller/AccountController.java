package trainingmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.ChangePassword;
import trainingmanagement.model.dto.InformationAccount;
import trainingmanagement.model.dto.auth.RegisterRequest;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.entity.User;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.security.UserDetail.UserLogin;
import trainingmanagement.service.UserService;

@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final UserService userService;
    private final UserLogin userLogin;

    @GetMapping("/informationAccount")
    public ResponseEntity<?> informationAccount(){
        User user = userLogin.userLogin();
        InformationAccount informationPersonal = userService.entityMap(user);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        informationPersonal
                ), HttpStatus.OK);
    }

    @PostMapping("/updateAccount")
    public ResponseEntity<?> updateAccount(@RequestBody @Valid RegisterRequest registerRequest) throws CustomException {
        User user = userService.updateAcc(registerRequest,userLogin.userLogin().getId());
        InformationAccount informationPersonal = userService.entityMap(user);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        informationPersonal
                ), HttpStatus.OK);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid ChangePassword changePassword) throws CustomException {
        User user = userService.updatePassword(changePassword,userLogin.userLogin().getId());
        InformationAccount informationPersonal = userService.entityMap(user);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Change success"
                ), HttpStatus.OK);
    }
}
