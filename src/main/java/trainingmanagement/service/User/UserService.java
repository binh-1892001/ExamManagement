package trainingmanagement.service.User;

import trainingmanagement.model.dto.admin.request.LoginRequest;
import trainingmanagement.model.dto.admin.request.RegisterRequest;
import trainingmanagement.model.dto.admin.response.JwtResponse;
import trainingmanagement.model.dto.admin.response.UserResponse;
import trainingmanagement.model.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllToList();
    List<UserResponse> getAllUserResponsesToList();
    Optional<User> getById(Long userId);
    Optional<UserResponse> getUserResponseById(Long userId);
    void deleteById(Long userId);
    JwtResponse handleLogin(LoginRequest userLogin);
    User addUser(RegisterRequest registerRequest);
    User save(User users);
    User updateAcc(RegisterRequest registerRequest, Long id);
    Optional<User> getByUsername(String username);
    List<UserResponse> findByUsernameOrFullNameContainingIgnoreCase(String keyword);
    UserResponse entityMap(User user);
    User entityMap(RegisterRequest userRequest);
//    List<UserResponse> getAllStudentInClassroom(Long userId);
    List<UserResponse> getAllTeacher();
    List<UserResponse> getAllStudentByClassId(Long classId);
}