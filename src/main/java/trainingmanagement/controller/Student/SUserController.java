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
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.entity.User;
import trainingmanagement.security.UserDetail.UserLogin;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class SUserController {
    private final UserService userService;
    private final UserLogin userLogin;
    private final CommonService commonService;

    @GetMapping("/allStudentInClass/{classId}")
    public ResponseEntity<?> getAllClassesToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "className", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable Long classId
    ) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            User user = userLogin.userLogin();
            List<AUserResponse> userResponses = userService.getAllStudentByClassId(classId);
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
