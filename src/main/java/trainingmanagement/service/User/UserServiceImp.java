package trainingmanagement.service.User;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.request.UserLoginRequest;
import trainingmanagement.model.dto.request.UserRegisterRequest;
import trainingmanagement.model.dto.response.JwtResponse;
import trainingmanagement.model.dto.response.UserResponse;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.EGender;
import trainingmanagement.model.entity.Role;
import trainingmanagement.model.entity.User;
import trainingmanagement.repository.UserRepository;
import trainingmanagement.security.Jwt.JwtProvider;
import trainingmanagement.security.UserDetail.UserPrincipal;
import trainingmanagement.service.Role.RoleService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtProvider jwtProvider;
    private final AuthenticationProvider authenticationProvider;
    @Override
    public List<User> getAllToList() {
        return userRepository.findAll();
    }

    @Override
    public List<UserResponse> getAllUserResponsesToList() {
        return getAllToList().stream().map(this::entityMap).toList();
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    @Override
    public JwtResponse handleLogin(UserLoginRequest userLoginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("tài khoản hoặc mật khẩu sai");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if(userPrincipal.getUser().getStatus() == null)
            throw new RuntimeException("your account is blocked");
        return JwtResponse.builder()
                .accessToken(jwtProvider.generateToken(userPrincipal))
                .fullName(userPrincipal.getUser().getFullName())
                .username(userPrincipal.getUsername())
                .status(userPrincipal.getUser().getStatus() == EActiveStatus.ACTIVE)
                .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public String addUser(UserRegisterRequest userRegisterRequest) {
        if(userRepository.existsByUsername(userRegisterRequest.getUsername()))
            throw new RuntimeException("username is exists");
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName(userRegisterRequest.getRole()));
        User users = User.builder()
                .fullName(userRegisterRequest.getFullName())
                .username(userRegisterRequest.getUsername())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .email(userRegisterRequest.getEmail())
                .avatar(userRegisterRequest.getAvatar())
                .phone(userRegisterRequest.getPhone())
                .dateOfBirth(userRegisterRequest.getDateOfBirth())
                .status(EActiveStatus.ACTIVE)
                .gender(userRegisterRequest.getGender() ? EGender.MALE : EGender.FEMALE)
                .roles(roles)
                .build();
        userRepository.save(users);
        return "Success";
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User save(User users) {
        return userRepository.save(users);
    }

    @Override
    public User updateAcc(UserRegisterRequest userRegisterRequest, Long id) {
        if(userRepository.existsByUsername(userRegisterRequest.getUsername()))
            throw new RuntimeException("username is exists");
        User userOld = findById(id);
        Set<Role> roles = userOld.getRoles();
        User users = User.builder()
                .fullName(userRegisterRequest.getFullName())
                .username(userRegisterRequest.getUsername())
                .password(userOld.getPassword())
                .email(userRegisterRequest.getEmail())
                .avatar(userRegisterRequest.getAvatar())
                .phone(userRegisterRequest.getPhone())
                .dateOfBirth(userRegisterRequest.getDateOfBirth())
                .status(EActiveStatus.ACTIVE)
                .gender(userRegisterRequest.getGender() ? EGender.MALE : EGender.FEMALE)
                .roles(roles)
                .build();
        users.setId(id);
        users.setCreateBy(userOld.getCreateBy());
        return userRepository.save(users);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> SearchByFullNameOrUsername(String username, String fullname) {
        return userRepository.findByUsernameOrFullName(username, fullname);
    }

    @Override
    public UserResponse entityMap(User user) {
        return UserResponse.builder()
            .fullName(user.getFullName())
            .username(user.getUsername())
            .password(user.getPassword())
            .email(user.getEmail())
            .phone(user.getPhone())
            .avatar(user.getAvatar())
            .dateOfBirth(user.getDateOfBirth())
            .gender(user.getGender().name())
            .status(user.getStatus().name())
            .build();
    }
}
