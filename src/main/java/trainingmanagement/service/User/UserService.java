package trainingmanagement.service.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.model.dto.requestEntity.UserLogin;
import trainingmanagement.model.dto.requestEntity.UserRegister;
import trainingmanagement.model.dto.responseEntity.JwtResponse;
import trainingmanagement.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> getAll(Pageable pageable);
    JwtResponse handleLogin(UserLogin userLogin);
    String addUser(UserRegister userRegister);
    User findById(Long id);
    void delete(Long id);
    User save(User users);
    User updateAcc(UserRegister userRegister, Long id);
    Optional<User> findByUsername(String username);
    List<User> SearchByFullNameOrUsername(String username, String fullname);
}
