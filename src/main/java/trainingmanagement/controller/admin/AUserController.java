package trainingmanagement.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.response.admin.AUserResponse;
import trainingmanagement.model.entity.Role;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.entity.User;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.security.UserDetail.UserLogin;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.RoleService;
import trainingmanagement.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/v1/admin/users")
public class AUserController {
    private final UserService userService;
    private final CommonService commonService;
    private final UserLogin userLogin;
    private final RoleService roleService;
    // * Get all users to pages.
    @GetMapping
    public ResponseEntity<?> getAllUsersToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "username", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException{
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AUserResponse> userResponses = userService.getAllUserResponsesToList();
            Page<?> users = commonService.convertListToPages(pageable, userResponses);
            if (!users.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                users.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Users page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Users page is out of range.");
        }
    }
    // * Get user by id.
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) throws CustomException{
        Optional<AUserResponse> user = userService.getAUserResponseById(userId);
        if(user.isEmpty()) throw new CustomException("User is not exists.");
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                user.get()
            ), HttpStatus.OK);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateAccount(@RequestBody UserRegisterRequest userRegisterRequest, @PathVariable("id") Long id) {
//        User user = userService.updateAcc(userRegisterRequest, id);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
    // * Delete user by id.
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("userId") Long userId) throws CustomException{
        Optional<User> deleteUser = userService.getUserById(userId);
        if(deleteUser.isPresent()){
            if (deleteUser.get().getId().equals(userLogin.userLogin().getId())){
                throw new CustomException("Cant delete this account");
            }
            userService.deleteById(userId);
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
    }
    // * Switch user status.
    @PutMapping("/switchStatus/{userId}")
    public ResponseEntity<?> switchUserStatus(@PathVariable("userId") Long userId) throws CustomException {
        Optional<User> updateUser = userService.getUserById(userId);
        if(updateUser.isPresent()) {
            User user = updateUser.get();
            if (user.getId().equals(userLogin.userLogin().getId())){
                throw new CustomException("Cant switch this account status");
            }
            user.setStatus(user.getStatus() == EActiveStatus.ACTIVE ? EActiveStatus.INACTIVE : EActiveStatus.ACTIVE);
            User updatedUser = userService.save(user);
            return new ResponseEntity<>(
                new ResponseWrapper<>(
                    EHttpStatus.SUCCESS,
                    HttpStatus.OK.value(),
                    HttpStatus.OK.name(),
                    updatedUser
                ), HttpStatus.OK);
        }
        // ? Xử lý Exception cần tìm được user theo id trước khi khoá/mở khoá trong Controller.
        throw new CustomException("User is not exists.");
    }

    // * Switch user permission.
    @PutMapping("/switchPermission/{userId}")
    public ResponseEntity<?> switchPermissionStatus(@PathVariable("userId") Long userId) throws CustomException {
        Optional<User> updateUser = userService.getUserById(userId);
        if(updateUser.isPresent()) {
            User user = updateUser.get();
            if (user.getId().equals(userLogin.userLogin().getId())){
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
                            updatedUser
                    ), HttpStatus.OK);
        }
        // ? Xử lý Exception cần tìm được user theo id trước khi khoá/mở khoá trong Controller.
        throw new CustomException("User is not exists.");
    }

    // * Find Users by username or fullName.
    @GetMapping("/search")
    public ResponseEntity<?> searchByFullNameOrUserName(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "username", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    )throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AUserResponse> userResponses = userService.findByUsernameOrFullNameContainingIgnoreCase(keyword);
            Page<?> users = commonService.convertListToPages(pageable, userResponses);
            if (!users.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                users.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Users page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Users page is out of range.");
        }
    }
    // * lấy về danh sách teacher
    @GetMapping("/allTeacher")
    public ResponseEntity<?> getAllTeacherToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "username", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException{
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AUserResponse> userResponses = userService.getAllTeacher();
            Page<?> users = commonService.convertListToPages(pageable, userResponses);
            if (!users.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                users.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Users page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Users page is out of range.");
        }
    }

}
