package trainingmanagement.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.model.dto.request.UserRegisterRequest;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.User;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.User.UserService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/v1/admin/users")
public class UserController {
    private final UserService userService;
    private final CommonService commonService;
    @GetMapping
    public ResponseEntity<?> getAllUser(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "username", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        Page<User> users = userService.getAll(pageable);
        return new ResponseEntity<>(users.getContent(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> handleRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        return new ResponseEntity<>(userService.addUser(userRegisterRequest),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateAccount(@RequestBody UserRegisterRequest userRegisterRequest, @PathVariable("id") Long id) {
        User user = userService.updateAcc(userRegisterRequest, id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("{userId}")
    public ResponseEntity<?> updateStatusUser(@PathVariable("userId") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>("người dùng không tồn tại", HttpStatus.BAD_REQUEST);
        } else {
            if(user.getStatus() == EActiveStatus.ACTIVE)
                user.setStatus(EActiveStatus.INACTIVE);
            else user.setStatus(EActiveStatus.ACTIVE);
            userService.save(user);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<?> findByName(@RequestParam(name = "nameSearch") String keyword) {
        List<User> user = userService.SearchByFullNameOrUsername(keyword, keyword);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
