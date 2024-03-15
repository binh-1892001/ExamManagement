package trainingmanagement.service.User;

import trainingmanagement.model.dto.request.admin.LoginRequest;
import trainingmanagement.model.dto.request.admin.RegisterRequest;
import trainingmanagement.model.dto.response.admin.JwtResponse;
import trainingmanagement.model.dto.response.admin.UserResponse;
import trainingmanagement.model.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllToList();
    List<UserResponse> getAllUserResponsesToList();
    Optional<User> getById(Long userId);
    void deleteById(Long userId);
    JwtResponse handleLogin(LoginRequest userLogin);
    User handleRegister(RegisterRequest registerRequest);
    User save(User users);
    User updateAcc(RegisterRequest registerRequest, Long id);
    Optional<User> getByUsername(String username);
    List<UserResponse> findByUsernameOrFullNameContainingIgnoreCase(String keyword);
    UserResponse entityMap(User user);
    User entityMap(RegisterRequest userRequest);
}