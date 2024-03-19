package trainingmanagement.service;

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
    JwtResponse handleLogin(LoginRequest userLogin);
    User save(User users);
    User updateAcc(RegisterRequest registerRequest, Long id);
    User handleRegister(RegisterRequest RegisterRequest);
    List<AUserResponse> findByUsernameOrFullNameContainingIgnoreCase(String keyword);
    User entityMap(RegisterRequest userRequest);
//    List<UserResponse> getAllStudentInClassroom(Long userId);
    List<AUserResponse> getAllTeacher();
    List<AUserResponse> getAllStudentByClassId(Long classId);
    AUserResponse entityAMap(User user);
}