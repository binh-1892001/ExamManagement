package trainingmanagement.service.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.model.dto.request.UserLoginRequest;
import trainingmanagement.model.dto.request.UserRegisterRequest;
import trainingmanagement.model.dto.response.JwtResponse;
import trainingmanagement.model.dto.response.UserResponse;
import trainingmanagement.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllToList();
    List<UserResponse> getAllUserResponsesToList();
    Page<User> getAll(Pageable pageable);
    JwtResponse handleLogin(UserLoginRequest userLogin);
    String addUser(UserRegisterRequest userRegisterRequest);
    User findById(Long id);
    void delete(Long id);
    User save(User users);
    User updateAcc(UserRegisterRequest userRegisterRequest, Long id);
    Optional<User> findByUsername(String username);
    List<User> SearchByFullNameOrUsername(String username, String fullname);
    UserResponse entityMap(User user);
}