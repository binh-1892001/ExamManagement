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

import java.util.*;
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
        if(userPrincipal.getUser().getStatus() == null
        || userPrincipal.getUser().getStatus() == EActiveStatus.INACTIVE)
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
    public User handleRegister(RegisterRequest RegisterRequest) {
        if(userRepository.existsByUsername(RegisterRequest.getUsername()))
            throw new RuntimeException("Username is exists");
        EGender userGender = null;
        if(RegisterRequest.getGender() != null)
            userGender = switch (RegisterRequest.getGender().toUpperCase()){
                case "MALE" -> EGender.MALE;
                case "FEMALE" -> EGender.FEMALE;
                default -> null;
            };
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.findByRoleName(ERoleName.ROLE_STUDENT));
        User users = User.builder()
            .fullName(RegisterRequest.getFullName())
            .username(RegisterRequest.getUsername())
            .password(passwordEncoder.encode(RegisterRequest.getPassword()))
            .email(RegisterRequest.getEmail())
            .avatar(RegisterRequest.getAvatar())
            .phone(RegisterRequest.getPhone())
            .dateOfBirth(RegisterRequest.getDateOfBirth())
            .status(EActiveStatus.INACTIVE)
            .gender(userGender)
            .roles(userRoles)
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
    public User updateAcc(RegisterRequest RegisterRequest, Long id) {
        if(userRepository.existsByUsername(RegisterRequest.getUsername()))
            throw new RuntimeException("username is exists");
        User userOld = getById(id).get();
        Set<Role> roles = userOld.getRoles();
        User users = User.builder()
            .fullName(RegisterRequest.getFullName())
            .username(RegisterRequest.getUsername())
            .password(userOld.getPassword())
            .email(RegisterRequest.getEmail())
            .avatar(RegisterRequest.getAvatar())
            .phone(RegisterRequest.getPhone())
            .dateOfBirth(RegisterRequest.getDateOfBirth())
            .status(EActiveStatus.ACTIVE)
            .gender(RegisterRequest.getGender().equalsIgnoreCase(EGender.MALE.name())
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

    @Override
    public AUserResponse entityMap(User user) {
        return AUserResponse.builder()
            .fullName(user.getFullName())
            .username(user.getUsername())
            .email(user.getEmail())
            .phone(user.getPhone())
            .avatar(user.getAvatar())
            .dateOfBirth(user.getDateOfBirth())
            .gender(user.getGender())
            .status(user.getStatus())
            .build();
    }
}