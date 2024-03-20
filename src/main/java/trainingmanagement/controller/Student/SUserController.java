package trainingmanagement.controller.Student;

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
import trainingmanagement.model.entity.UserClass;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.entity.User;
import trainingmanagement.security.UserDetail.UserLogin;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.UserClassService;
import trainingmanagement.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/student")
public class SUserController {
    private final UserService userService;
    private final UserClassService userClassService;
    private final CommonService commonService;

    // * Get all students to pages.
    @GetMapping("/allStudentInClass/{classId}")
    public ResponseEntity<?> getAllClassesToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable Long classId
    ) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<UserClass> userClasses = userClassService.findByClassId(classId);
            List<AUserResponse> users = new ArrayList<>();
            for (UserClass userClass : userClasses) {
                EActiveStatus isActive = Objects.requireNonNull(userService.getById(userClass.getUser().getId()).orElse(null)).getStatus();
                if (isActive == EActiveStatus.ACTIVE) {
                    users.add(userService.getUserResponseById(userClass.getUser().getId()).orElse(null));
                }
            }
            Page<?> usersDisplay = commonService.convertListToPages(pageable, users);
            if (!users.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                usersDisplay.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Users page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Users page is out of range.");
        }
    }

}
