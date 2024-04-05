package trainingmanagement.controller.student;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.entity.UserClass;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.security.UserDetail.UserLoggedIn;
import trainingmanagement.service.ClassroomService;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.UserClassService;
import trainingmanagement.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/student")
public class SClassController {
    private final ClassroomService classroomService;
    private final UserLoggedIn userLogin;
    private final UserClassService userClassService;
    private final UserService userService;
    private final CommonService commonService;

    @GetMapping("/class")
    public ResponseEntity<?> getInformationClass(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<UserClass> userClasses = userClassService.findClassByStudent(userLogin.getUserLoggedIn().getId());
            List<AClassResponse> classes = new ArrayList<>();
            for (UserClass userClass : userClasses) {
                EActiveStatus isActive = Objects.requireNonNull(userService.getUserById(userClass.getUser().getId()).orElse(null)).getStatus();
                if (isActive == EActiveStatus.ACTIVE) {
                    classes.add(classroomService.getAClassById(userClass.getClassroom().getId()));
                }
            }
            Page<?> classrooms = commonService.convertListToPages(pageable, classes);
            if (!classrooms.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                classrooms.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Classes page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Classes page is out of range.");
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }
}
