package trainingmanagement.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.InterpretationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.exception.ResourceNotFoundException;
import trainingmanagement.model.dto.auth.RegisterRequest;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.response.admin.AUserResponse;
import trainingmanagement.model.entity.Role;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.entity.User;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.security.UserDetail.UserLoggedIn;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.RoleService;
import trainingmanagement.service.UserService;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/users")
public class AUserController {
    private final UserService userService;
    private final UserLoggedIn userLogin;
    private final RoleService roleService;

    // * Get all users to pages.
    @GetMapping
    public ResponseEntity<?> getAllUsersToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "username", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        try{
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        Page<AUserResponse> userResponses = userService.getAllUserResponsesToList(pageable);
        if (userResponses.getContent().isEmpty()) throw new CustomException("Users page is empty.");
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        userResponses.getContent()
                ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    //* Create account
    @PostMapping("/createAccount")
    public ResponseEntity<?> handleRegister(@RequestBody @Valid RegisterRequest RegisterRequest) throws CustomException {
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        userService.entityMap(userService.handleRegister(RegisterRequest))
                ), HttpStatus.CREATED);
    }

    // * Get user by id.
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") String userId) throws CustomException {
        try {
            Long id = Long.parseLong(userId);
            Optional<AUserResponse> user = userService.getAUserResponseById(id);
            if (user.isEmpty()) throw new CustomException("User is not exists.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            user.get()
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * Delete user by id.
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("userId") String userId) throws CustomException {
        try {
            Long id = Long.parseLong(userId);
            Optional<User> deleteUser = userService.getUserById(id);
            if (deleteUser.isPresent()) {
                if (deleteUser.get().getId().equals(userLogin.getUserLoggedIn().getId())) {
                    throw new CustomException("Cant delete this account");
                }
                userService.deleteById(id);
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                "Delete user successfully."
                        )
                        , HttpStatus.OK);
            }
            // ? Xử lý Exception cần tìm được user theo id trước khi khoá/mở khoá trong Controller.
            throw new CustomException("User is not exists.");
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * Switch user status.
    @PutMapping("/switchStatus/{userId}")
    public ResponseEntity<?> switchUserStatus(@PathVariable("userId") String userId) throws CustomException {
        try {
            Long id = Long.parseLong(userId);
            Optional<User> updateUser = userService.getUserById(id);
            if (updateUser.isPresent()) {
                User user = updateUser.get();
                if (user.getId().equals(userLogin.getUserLoggedIn().getId())) {
                    throw new CustomException("Cant switch this account status");
                }
                user.setStatus(user.getStatus() == EActiveStatus.ACTIVE ? EActiveStatus.INACTIVE : EActiveStatus.ACTIVE);
                User updatedUser = userService.save(user);
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                userService.entityAMap(updatedUser)
                        ), HttpStatus.OK);
            }
            // ? Xử lý Exception cần tìm được user theo id trước khi khoá/mở khoá trong Controller.
            throw new CustomException("User is not exists.");
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * Switch user permission.
    @PutMapping("/switchPermission/{userId}")
    public ResponseEntity<?> switchPermissionStatus(@PathVariable("userId") String userId) throws CustomException {
        try {
            Long id = Long.parseLong(userId);
            Optional<User> updateUser = userService.getUserById(id);
            if (updateUser.isPresent()) {
                User user = updateUser.get();
                if (user.getId().equals(userLogin.getUserLoggedIn().getId())) {
                    throw new CustomException("Cant switch this account permission");
                }
                Set<Role> roles = user.getRoles();
                Set<Role> newRoles = new HashSet<>();

                for (Role role : roles) {
                    if (role.getRoleName().equals(ERoleName.ROLE_TEACHER)) {
                        Role studentRole = roleService.findByRoleName(ERoleName.ROLE_STUDENT);
                        newRoles.add(studentRole);
                    } else {
                        Role teacherRole = roleService.findByRoleName(ERoleName.ROLE_TEACHER);
                        newRoles.add(teacherRole);
                    }
                }
                user.setRoles(newRoles);
                User updatedUser = userService.save(user);
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                userService.entityAMap(updatedUser)
                        ), HttpStatus.OK);
            }
            // ? Xử lý Exception cần tìm được user theo id trước khi khoá/mở khoá trong Controller.
            throw new CustomException("User is not exists.");
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * Find Users by username or fullName.
    @GetMapping("/search")
    public ResponseEntity<?> searchByFullNameOrUserName(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "username", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        try {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        Page<AUserResponse> userResponses = userService.findByUsernameOrFullNameContainingIgnoreCase(keyword, pageable);
        if (userResponses.getContent().isEmpty()) throw new CustomException("User page is empty.");
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        userResponses.getContent()
                ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    // * lấy về danh sách teacher
    @GetMapping("/allTeacher")
    public ResponseEntity<?> getAllTeacherToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "username", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        try {
            Pageable pageable;
            if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<AUserResponse> userResponses = userService.getAllTeacher(pageable);
            if (userResponses.getContent().isEmpty()) throw new CustomException("Users page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            userResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }
}
