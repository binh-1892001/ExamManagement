package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
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
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.ChangeInformation;
import trainingmanagement.model.dto.ChangePassword;
import trainingmanagement.model.dto.InformationAccount;
import trainingmanagement.model.dto.auth.JwtResponse;
import trainingmanagement.model.dto.auth.LoginRequest;
import trainingmanagement.model.dto.auth.RegisterRequest;
import trainingmanagement.model.dto.response.admin.AUserResponse;
import trainingmanagement.model.entity.Role;
import trainingmanagement.model.entity.User;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EGender;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.repository.UserRepository;
import trainingmanagement.security.Jwt.JwtProvider;
import trainingmanagement.security.UserDetail.UserPrincipal;
import trainingmanagement.service.RoleService;
import trainingmanagement.service.UserService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationProvider authenticationProvider;

    @Override
    public Page<User> getAllToList(Pageable pageable) {
        return userRepository.getAllUserExceptAdmin(pageable);
    }

    // *lấy danh sách người dùng ngoại trừ admin
    @Override
    public Page<AUserResponse> getAllUserResponsesToList(Pageable pageable) {
        return getAllToList(pageable).map(this::entityAMap);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // * tìm kiếm người dùng theo id
    @Override
    public Optional<AUserResponse> getAUserResponseById(Long userId) {
        Optional<User> optionalUser = getUserById(userId);
        return optionalUser.map(this::entityAMap);
    }

    // * tìm kiếm người dùng theo userName
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    // * đăng nhập
    @Override
    public JwtResponse handleLogin(LoginRequest loginRequest) throws CustomException {
        Authentication authentication;
        try {
            authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new CustomException("Username or password is wrong.");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!(userPrincipal.getUser().getStatus() == EActiveStatus.ACTIVE))
            throw new CustomException("This account is inactive.");
        return JwtResponse.builder()
                .accessToken(jwtProvider.generateToken(userPrincipal))
                .fullName(userPrincipal.getUser().getFullName())
                .username(userPrincipal.getUsername())
                .status(userPrincipal.getUser().getStatus() == EActiveStatus.ACTIVE)
                .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    //* tạo tài khoản
    @Override
    public User handleRegister(RegisterRequest registerRequest) throws CustomException {
        if (userRepository.existsByUsername(registerRequest.getUsername()))
            throw new CustomException("Username is exists");
        EGender userGender = null;
        if (registerRequest.getGender() != null)
            userGender = switch (registerRequest.getGender().toUpperCase()) {
                case "MALE" -> EGender.MALE;
                case "FEMALE" -> EGender.FEMALE;
                default -> null;
            };
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.findByRoleName(ERoleName.ROLE_STUDENT));
        User users = User.builder()
                .fullName(registerRequest.getFullName())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .avatar(registerRequest.getAvatar())
                .phone(registerRequest.getPhone())
                .dateOfBirth(LocalDate.parse(registerRequest.getDateOfBirth()))
                .status(EActiveStatus.INACTIVE)
                .gender(userGender)
                .roles(userRoles)
                .build();
        return userRepository.save(users);
    }

    // * xóa tài khoản
    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User save(User users) {
        return userRepository.save(users);
    }

    //* cập nhật tài khoản
    @Override
    public User updateAcc(ChangeInformation changeInformation, Long userId) throws CustomException {
        Optional<User> userOldOptional = getUserById(userId);
        User userOld = userOldOptional.get();
        Set<Role> roles = userOld.getRoles();
        User user = User.builder()
                .fullName(changeInformation.getFullName())
                .password(userOld.getPassword())
                .email(changeInformation.getEmail())
                .avatar(changeInformation.getAvatar())
                .phone(changeInformation.getPhone())
                .dateOfBirth(LocalDate.parse(changeInformation.getDateOfBirth()))
                .status(EActiveStatus.ACTIVE)
                .gender(changeInformation.getGender().equalsIgnoreCase(EGender.MALE.name())
                        ? EGender.MALE : EGender.FEMALE)
                .roles(roles)
                .build();
        user.setId(userOld.getId());
        user.setUsername(userOld.getUsername());
        user.setCreateBy(userOld.getCreateBy());
        user.setCreatedDate(userOld.getCreatedDate());
        return userRepository.save(user);
    }

    //* cập nhật mật khẩu
    @Override
    public User updatePassword(ChangePassword changePassword, Long userId) throws CustomException {
        Optional<User> userOptional = getUserById(userId);
        User user = userOptional.get();
        if (!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
            throw new CustomException("Old password not true!");
        } else if (changePassword.getOldPassword().equals(changePassword.getNewPassword())) {
            throw new CustomException("New password like old password!");
        } else if (!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
            throw new CustomException("Confirm password not like new password");
        }
        user.setPassword(passwordEncoder.encode(changePassword.getConfirmPassword()));
        return userRepository.save(user);
    }

    @Override
    public Set<Role> getAllRolesByUser(User user) {
        return user.getRoles();
    }

    //* tìm kiếm theo username or fullname
    @Override
    public Page<AUserResponse> findByUsernameOrFullNameContainingIgnoreCase(String keyword, Pageable pageable) {
        return userRepository.findByUsernameOrFullNameContainingIgnoreCase(keyword, pageable).map(this::entityAMap);
    }

    @Override
    public User entityMap(RegisterRequest userRequest) {
        return User.builder()
                .fullName(userRequest.getFullName())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .avatar(userRequest.getAvatar())
                .dateOfBirth(LocalDate.parse(userRequest.getDateOfBirth()))
                .gender(userRequest.getGender().equalsIgnoreCase(EGender.MALE.name()) ? EGender.MALE : EGender.FEMALE)
                .status(EActiveStatus.INACTIVE)
                .build();
    }

    @Override
    public InformationAccount entityMap(User user) {
        return InformationAccount.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .build();
    }

    //* lấy tất cả danh sách giáo viên
    @Override
    public Page<AUserResponse> getAllTeacher(Pageable pageable) {
        Page<User> users = userRepository.getAllTeacher(pageable);
        return users.map(this::entityAMap);
    }

    @Override
    public AUserResponse entityAMap(User user) {
        return AUserResponse.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .status(user.getStatus())
                .createdDate(user.getCreatedDate())
                .modifyDate(user.getModifyDate())
                .createdBy(user.getCreateBy())
                .modifyBy(user.getModifyBy())
                .roles(user.getRoles().stream().map(Role::getRoleName))
                .build();
    }
}