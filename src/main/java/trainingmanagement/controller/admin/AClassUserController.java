package trainingmanagement.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AUserClassRequest;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.dto.response.admin.AUserResponse;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.entity.UserClass;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.ClassroomService;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.UserClassService;
import trainingmanagement.service.UserService;

import java.util.ArrayList;
import java.util.List;


@RestController
    @RequestMapping("/v1/admin/classUser")
@RequiredArgsConstructor
public class AClassUserController {
    private final UserClassService userClassService;
    private final UserService userService;
    private final ClassroomService classroomService;
    private final CommonService commonService;

    // * get all student by classId
    @GetMapping("/getAllStudent/{classId}")
    public ResponseEntity<?> getStudentByClassId(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable("classId") String classId
    ) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            Long idClass = Long.parseLong(classId);
            List<UserClass> userClasses = userClassService.findByClassId(idClass);
            List<AUserResponse> users = new ArrayList<>();
            for (UserClass userClass : userClasses) {
                users.add(userService.getAUserResponseById(userClass.getUser().getId()).orElse(null));
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
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Users page is out of range.");
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    // * get all class by userId
    @GetMapping("/getAllClass/{userId}")
    public ResponseEntity<?> getClassByUserId(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable("userId") String id) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            Long idUser = Long.parseLong(id);
            List<UserClass> userClasses = userClassService.findClassByStudent(idUser);
            List<AClassResponse> classes = new ArrayList<>();
            for (UserClass userClass : userClasses) {
                classes.add(classroomService.getAClassById(userClass.getClassroom().getId()));
            }
            Page<?> classDisplay = commonService.convertListToPages(pageable, classes);
            if (!classDisplay.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                classes
                        ), HttpStatus.OK);
            }
            throw new CustomException("Classes page is empty.");
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Classes page is out of range.");
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    // * add studentClass
    @PostMapping("/addStudentClass")
    public ResponseEntity<?> addStudentClass(@RequestBody @Valid AUserClassRequest userClassRequest)
            throws CustomException {
        userClassService.add(userClassRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "Add success"
                ), HttpStatus.CREATED);
    }

    // * update studentClass
    @PutMapping("/updateStudentClass/{id}")
    public ResponseEntity<?> updateStudentClass(
            @RequestBody @Valid AUserClassRequest userClassRequest
            , @PathVariable String id) throws CustomException {
        try {
            Long idUserClass = Long.parseLong(id);
            userClassService.update(userClassRequest, idUserClass);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.CREATED.value(),
                            HttpStatus.CREATED.name(),
                            "Update success"
                    ), HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * delete studentClass
    @DeleteMapping("/deleteStudentClass/{id}")
    public ResponseEntity<?> deleteStudentClass(@PathVariable String id) throws CustomException {
        try {
            Long idUserClass = Long.parseLong(id);
            userClassService.deleteById(idUserClass);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.CREATED.value(),
                            HttpStatus.CREATED.name(),
                            "Delete success"
                    ), HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

}
