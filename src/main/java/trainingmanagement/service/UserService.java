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
    Optional<User> getById(Long userId);
    void deleteById(Long userId);
    JwtResponse handleLogin(LoginRequest userLogin);
    User handleRegister(RegisterRequest RegisterRequest);
    User save(User users);
    User updateAcc(RegisterRequest RegisterRequest, Long id);
    Optional<User> getByUsername(String username);
    List<AUserResponse> findByUsernameOrFullNameContainingIgnoreCase(String keyword);
    AUserResponse entityMap(User user);
    User entityMap(RegisterRequest userRequest);
}