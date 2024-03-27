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
import trainingmanagement.model.dto.request.admin.AClassSubjectRequest;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.dto.response.admin.ASubjectResponse;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.entity.ClassSubject;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.ClassSubjectService;
import trainingmanagement.service.ClassroomService;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.SubjectService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/admin/classSubject")
@RequiredArgsConstructor
public class AClassSubjectController {
    private final ClassSubjectService classSubjectService;
    private final ClassroomService classroomService;
    private final SubjectService subjectService;
    private final CommonService commonService;

    // * get all subject by classId
    @GetMapping("/getAllSubject/{classId}")
    public ResponseEntity<?> getSubjectByClassId(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable("classId") Long classId
    ) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<ClassSubject> classSubjects = classSubjectService.findSubjectByClassId(classId);
            List<ASubjectResponse> subjects = new ArrayList<>();
            for (ClassSubject classSubject : classSubjects) {
                subjects.add(subjectService.getASubjectResponseById(classSubject.getSubject().getId()));
            }
            Page<?> subjectsDisplay = commonService.convertListToPages(pageable, subjects);
            if (!subjects.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                subjectsDisplay.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Subjects page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Subjects page is out of range.");
        }
    }

    // * get all class by subjectId
    @GetMapping("/getAllClass/{subjectId}")
    public ResponseEntity<?> getClassBySubjectId(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable("subjectId") Long id) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<ClassSubject> classSubjects = classSubjectService.findClassBySubjectId(id);
            List<AClassResponse> classes = new ArrayList<>();
            for (ClassSubject classSubject : classSubjects) {
                classes.add(classroomService.getAClassById(classSubject.getClassroom().getId()));
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
        } catch (IllegalArgumentException e) {
            throw new CustomException("Classes page is out of range.");
        }
    }

    // * add subjectClass
    @PostMapping("/addSubjectClass")
    public ResponseEntity<?> addSubjectClass(@RequestBody @Valid AClassSubjectRequest aClassSubjectRequest) throws CustomException {
        classSubjectService.add(aClassSubjectRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "Add complete"
                ), HttpStatus.CREATED);
    }

    // * update subjectClass
    @PutMapping("/updateSubjectClass/{id}")
    public ResponseEntity<?> updateStudentClass(
            @RequestBody @Valid AClassSubjectRequest aClassSubjectRequest
            ,@PathVariable Long id) throws CustomException {
        classSubjectService.update(aClassSubjectRequest,id);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "Update success"
                ), HttpStatus.CREATED);
    }

    // * delete subjectClass
    @DeleteMapping("/deleteSubjectClass/{id}")
    public ResponseEntity<?> deleteStudentClass(@PathVariable Long id){
        classSubjectService.deleteById(id);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "Delete success"
                ), HttpStatus.CREATED);
    }
}
