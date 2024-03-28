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
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.response.admin.AUserResponse;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.entity.UserClass;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.UserService;
import trainingmanagement.service.UserClassService;

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
    public ResponseEntity<?> getStudentByClassId(@PathVariable("classId") Long id) throws CustomException {
        List<UserClass> userClasses = userClassService.findByClassId(id);
        List<AUserResponse> users = new ArrayList<>();
        for (UserClass userClass : userClasses) {
            EActiveStatus isActive = userService.getUserById(userClass.getUser().getId()).orElse(null).getStatus();
            if (isActive == EActiveStatus.ACTIVE) {
                users.add(userService.getAUserResponseById(userClass.getUser().getId()).orElse(null));
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
        Optional<AUserResponse> user = userService.getAUserResponseById(userId);
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
        Page<AUserResponse> userResponses = userService.findByUsernameOrFullNameContainingIgnoreCase(keyword, pageable);
        if (userResponses.getContent().isEmpty()) throw new CustomException("Users page is empty.");
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        userResponses.getContent()
                ), HttpStatus.OK);

    }
}
