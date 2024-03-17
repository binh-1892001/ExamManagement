package trainingmanagement.controller.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.Wrapper.ResponseWrapper;
import trainingmanagement.model.dto.admin.response.UserResponse;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.EHttpStatus;
import trainingmanagement.model.entity.UserClass;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.User.UserService;
import trainingmanagement.service.UserClass.UserClassService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/teacher/student")
@RequiredArgsConstructor
public class TStudentController {
    private final UserClassService userClassService;
    private final UserService userService;
    private final CommonService commonService;

    @GetMapping("/class/{classId}")
    public ResponseEntity<?> getStudentByClassId(@PathVariable("classId") Long id) throws CustomException{
        List<UserClass> userClasses = userClassService.findByClassId(id);
        List<UserResponse> users = new ArrayList<>();

        for (UserClass userClass : userClasses) {
            EActiveStatus isActive = userService.getById(userClass.getUser().getId()).orElse(null).getStatus();
            if (isActive == EActiveStatus.ACTIVE) {
                users.add(userService.getUserResponseById(userClass.getUser().getId()).orElse(null));
            }
        }
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        users), HttpStatus.OK);
    }

    // * Get user by id.
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) throws CustomException {
        Optional<UserResponse> user = userService.getUserResponseById(userId);
        if (user.isEmpty())
            throw new CustomException("User is not exists.");
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        user.get()
                ), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByFullNameOrUserName(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "username", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<UserResponse> userResponses = userService.findByUsernameOrFullNameContainingIgnoreCase(keyword);
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