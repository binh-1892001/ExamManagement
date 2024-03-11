package trainingmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainingmanagement.model.dto.requestEntity.UserLogin;
import trainingmanagement.model.dto.responseEntity.JwtResponse;
import trainingmanagement.service.User.UserService;

@RestController
@RequestMapping("/v1/auth/")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> handleLogin(@RequestBody UserLogin userLogin) {
        return new ResponseEntity<>(userService.handleLogin(userLogin), HttpStatus.OK);
    }
}
