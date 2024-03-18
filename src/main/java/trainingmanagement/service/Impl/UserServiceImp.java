package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.auth.LoginRequest;
import trainingmanagement.model.dto.auth.RegisterRequest;
import trainingmanagement.model.dto.auth.JwtResponse;
import trainingmanagement.model.dto.response.admin.AUserResponse;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EGender;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.model.entity.Role;
import trainingmanagement.model.entity.User;
import trainingmanagement.repository.UserRepository;
import trainingmanagement.security.Jwt.JwtProvider;
import trainingmanagement.security.UserDetail.UserPrincipal;
import trainingmanagement.service.RoleService;
import trainingmanagement.service.UserService;

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
    public List<AUserResponse> getAllUserResponsesToList() {
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
    public Optional<AUserResponse> getUserResponseById(Long userId) {
        return Optional.ofNullable(entityMap(userRepository.findByUserId(userId).orElse(null)));
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
    public List<AUserResponse> findByUsernameOrFullNameContainingIgnoreCase(String keyword) {
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

//    @Override
//    public List<UserResponse> getAllStudentInClassroom(Long userId) {
//        List<User> users = userRepository.getAllStudentByClassId(userId);
//        return users.stream().map(this::entityMap).toList();
//    }

    @Override
    public List<AUserResponse> getAllTeacher() {
        List<User> users = userRepository.getAllTeacher();
        return users.stream().map(this::entityMap).toList();
    }

    @Override
    public List<AUserResponse> getAllStudentByClassId(Long classId) {
        List<User> users =  userRepository.getAllByClassIdAndRole(ERoleName.ROLE_STUDENT,classId);
        return users.stream().map(this::entityMap).toList();
    }


    @Override
    public AUserResponse entityMap(User user) {
        return AUserResponse.builder()
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
