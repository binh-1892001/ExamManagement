package trainingmanagement.service.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.request.LoginRequest;
import trainingmanagement.model.dto.request.RegisterRequest;
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
    public JwtResponse handleLogin(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Username or password is wrong.");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if(userPrincipal.getUser().getStatus() == null)
            throw new RuntimeException("This account is inactive.");
        return JwtResponse.builder()
            .accessToken(jwtProvider.generateToken(userPrincipal))
            .fullName(userPrincipal.getUser().getFullName())
            .username(userPrincipal.getUsername())
            .status(userPrincipal.getUser().getStatus() == EActiveStatus.ACTIVE)
            .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
            .build();
    }

    @Override
    public User addUser(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername()))
            throw new RuntimeException("username is exists");
        User users = User.builder()
            .fullName(registerRequest.getFullName())
            .username(registerRequest.getUsername())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .email(registerRequest.getEmail())
            .avatar(registerRequest.getAvatar())
            .phone(registerRequest.getPhone())
            .dateOfBirth(registerRequest.getDateOfBirth())
            .status(EActiveStatus.INACTIVE)
            .gender(registerRequest.getGender().equalsIgnoreCase(EGender.MALE.name())
                    ? EGender.MALE : EGender.FEMALE)
            .build();
        return userRepository.save(users);
    }

    @Override
    public Optional<User> getById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User save(User users) {
        return userRepository.save(users);
    }

    @Override
    public User updateAcc(RegisterRequest registerRequest, Long id) {
        if(userRepository.existsByUsername(registerRequest.getUsername()))
            throw new RuntimeException("username is exists");
        User userOld = getById(id).get();
        Set<Role> roles = userOld.getRoles();
        User users = User.builder()
                .fullName(registerRequest.getFullName())
                .username(registerRequest.getUsername())
                .password(userOld.getPassword())
                .email(registerRequest.getEmail())
                .avatar(registerRequest.getAvatar())
                .phone(registerRequest.getPhone())
                .dateOfBirth(registerRequest.getDateOfBirth())
                .status(EActiveStatus.ACTIVE)
                .gender(registerRequest.getGender().equalsIgnoreCase(EGender.MALE.name())
                        ? EGender.MALE : EGender.FEMALE)
                .roles(roles)
                .build();
        users.setId(id);
        users.setCreateBy(userOld.getCreateBy());
        return userRepository.save(users);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserResponse> findByUsernameOrFullNameContainingIgnoreCase(String keyword) {
        return userRepository.findByUsernameOrFullNameContainingIgnoreCase(keyword)
                .stream().map(this::entityMap).toList();
    }

    @Override
    public User entityMap(RegisterRequest userRequest) {
        return User.builder()
            .fullName(userRequest.getFullName())
            .username(userRequest.getUsername())
            .email(userRequest.getEmail())
            .phone(userRequest.getPhone())
            .avatar(userRequest.getAvatar())
            .dateOfBirth(userRequest.getDateOfBirth())
            .gender(userRequest.getGender().equalsIgnoreCase(EGender.MALE.name()) ? EGender.MALE : EGender.FEMALE)
            .status(EActiveStatus.INACTIVE)
            .build();
    }

    @Override
    public List<UserResponse> getAllStudentInClassroom(Long userId) {
        List<User> users = userRepository.getAllStudentInClassroom(userId);
        return users.stream().map(this::entityMap).toList();
    }

    @Override
    public List<UserResponse> getAllTeacher() {
        List<User> users = userRepository.getAllTeacher();
        return users.stream().map(this::entityMap).toList();
    }

    @Override
    public List<UserResponse> getAllStudentByClassId(Long classId) {
        List<User> users =  userRepository.getAllStudentByClassId(classId);
        return users.stream().map(this::entityMap).toList();
    }


    @Override
    public UserResponse entityMap(User user) {
        return UserResponse.builder()
            .fullName(user.getFullName())
            .username(user.getUsername())
            .email(user.getEmail())
            .phone(user.getPhone())
            .avatar(user.getAvatar())
            .dateOfBirth(user.getDateOfBirth())
            .gender(user.getGender().name())
            .status(user.getStatus().name())
            .build();
    }
}
