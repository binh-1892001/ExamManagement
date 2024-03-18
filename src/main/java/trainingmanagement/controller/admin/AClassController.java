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
import trainingmanagement.model.dto.request.admin.AClassRequest;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.request.admin.AClassSubjectRequest;
import trainingmanagement.model.dto.request.admin.AUserClassRequest;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.entity.UserClass;
import trainingmanagement.service.ClassSubjectService;
import trainingmanagement.service.ClassroomService;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.UserClassService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin/classes")
@RequiredArgsConstructor
public class AClassController {
    private final CommonService commonService;
    private final ClassroomService classroomService;
    private final ClassSubjectService classSubjectService;
    private final UserClassService userClassService;
    // * Get all classes to pages.
    @GetMapping
    public ResponseEntity<?> getAllClassesToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "className", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AClassResponse> classroomResponses = classroomService.getAllClassResponsesToList();
            Page<?> classrooms = commonService.convertListToPages(pageable, classroomResponses);
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
        }
    }
    // * Get classroom by id.
    @GetMapping("/{classId}")
    public ResponseEntity<?> getClassById(@PathVariable("classId") Long classId) throws CustomException{
        Optional<Classroom> classroom = classroomService.getById(classId);
        if(classroom.isPresent())
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        classroom.get()
                    ), HttpStatus.OK);
        throw new CustomException("Class is not exists.");
    }
    // * Create new classroom.
    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody AClassRequest AClassRequest) {
        Classroom classroom = classroomService.save(AClassRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                    EHttpStatus.SUCCESS,
                    HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.name(),
                    classroom
            ), HttpStatus.CREATED);
    }
    // * Update an existed classroom.
    @PatchMapping("/{classId}")
    public ResponseEntity<?> pathUpdateClass(
            @PathVariable("classId") Long updateClassroomId,
            @RequestBody AClassRequest AClassRequest
    ) {
        Classroom classroom = classroomService.patchUpdate(updateClassroomId, AClassRequest);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                    EHttpStatus.SUCCESS,
                    HttpStatus.OK.value(),
                    HttpStatus.OK.name(),
                    classroom
            ), HttpStatus.OK);
    }
    // * Delete an existed classroom.
    @DeleteMapping("/{classId}")
    public ResponseEntity<?> deleteClassById(@PathVariable("classId") Long classId) {
        classroomService.deleteById(classId);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Delete class successfully."
            ), HttpStatus.OK);
    }
    // * Find classroom by className.
    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "className", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AClassResponse> classroomResponses = classroomService.findByClassName(keyword);
            Page<?> classrooms = commonService.convertListToPages(pageable, classroomResponses);
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
        }
    }
    // * add subject to class
    @PostMapping("/addSubject")
    public ResponseEntity<?> addSubjectToClass(@RequestBody AClassSubjectRequest AClassSubjectRequest){
        classSubjectService.save(AClassSubjectRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "Add complete"
                ), HttpStatus.CREATED);
    }

    // * add student to class
    @PostMapping("/saveStudent")
    public ResponseEntity<?> saveStudent(@RequestBody AUserClassRequest AUserClassRequest){
        UserClass userClass = userClassService.saveStudent(AUserClassRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        userClass
                ), HttpStatus.CREATED);
    }

 }
