package trainingmanagement.service;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.ChangePassword;
import trainingmanagement.model.dto.InformationAccount;
import trainingmanagement.model.dto.auth.LoginRequest;
import trainingmanagement.model.dto.auth.RegisterRequest;
import trainingmanagement.model.dto.auth.JwtResponse;
import trainingmanagement.model.dto.response.admin.AUserResponse;
import trainingmanagement.model.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllToList();
    List<AUserResponse> getAllUserResponsesToList();
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByUsername(String username);
    Optional<AUserResponse> getAUserResponseById(Long userId);
    void deleteById(Long userId);
    JwtResponse handleLogin(LoginRequest userLogin) throws CustomException;
    User save(User users);
    User updateAcc(RegisterRequest registerRequest,Long userId) throws CustomException;
    User updatePassword(ChangePassword ChangePassword, Long userId) throws CustomException;
    User handleRegister(RegisterRequest RegisterRequest) throws CustomException;
    List<AUserResponse> findByUsernameOrFullNameContainingIgnoreCase(String keyword);
    User entityMap(RegisterRequest userRequest);
    InformationAccount entityMap(User user);
    List<AUserResponse> getAllTeacher();
    AUserResponse entityAMap(User user);
}