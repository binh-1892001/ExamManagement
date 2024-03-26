package trainingmanagement.service;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.ChangeInformation;
import trainingmanagement.model.dto.ChangePassword;
import trainingmanagement.model.dto.InformationAccount;
import trainingmanagement.model.dto.auth.LoginRequest;
import trainingmanagement.model.dto.auth.RegisterRequest;
import trainingmanagement.model.dto.auth.JwtResponse;
import trainingmanagement.model.dto.response.admin.AUserResponse;
import trainingmanagement.model.entity.Role;
import trainingmanagement.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    List<User> getAllToList();
    List<AUserResponse> getAllUserResponsesToList();
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByUsername(String username);
    Optional<AUserResponse> getAUserResponseById(Long userId);
    void deleteById(Long userId);
    JwtResponse handleLogin(LoginRequest userLogin) throws CustomException;
    User save(User users);
    User updateAcc(ChangeInformation changeInformation, Long userId) throws CustomException;
    User updatePassword(ChangePassword newPassword, Long userId) throws CustomException;
    User handleRegister(RegisterRequest registerRequest) throws CustomException;
    Set<Role> getAllRolesByUser(User user);
    List<AUserResponse> findByUsernameOrFullNameContainingIgnoreCase(String keyword);
    User entityMap(RegisterRequest userRequest);
    InformationAccount entityMap(User user);
    List<AUserResponse> getAllTeacher();
    AUserResponse entityAMap(User user);
}