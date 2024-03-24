package trainingmanagement.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AClassRequest;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.ClassroomService;

@RestController
@RequestMapping("/v1/admin/classes")
@RequiredArgsConstructor
public class AClassController {
    private final ClassroomService classroomService;
    // * Get all classes to pages.
    @GetMapping
    public ResponseEntity<?> getAllClassesToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "Id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        Page<Classroom> classes = classroomService.getAllClassToPages(limit, page, sort, order);
        Page<AClassResponse> classResponses = classroomService.entityAMap(classes);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                    HttpStatus.OK.value(),
                    HttpStatus.OK.name(),
                    classResponses.getContent()
            ), HttpStatus.OK);
    }
    // * Get classroom by id.
    @GetMapping("/{classId}")
    public ResponseEntity<?> getClassById(@PathVariable("classId") Long classId) throws CustomException{
        AClassResponse classroom = classroomService.getAClassById(classId);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                classroom
            ), HttpStatus.OK);
    }
    /**
     * ? Controller gọi đến Service tạo mới và lưu 1 đối tượng Class vào Db dành cho Admin.
     * @Param: AClassRequest dto của Admin dùng để lưu vào trong Db.
     * @Return: trả về 1 ResponseEntity đại diện cho Class đã lưu thành công vào Db.
     * */
    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody @Valid AClassRequest classRequest) throws CustomException {
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                classroomService.createClass(classRequest)
        ), HttpStatus.CREATED);
    }
    // * patchUpdate an exists classroom.
    @PatchMapping("/{classId}")
    public ResponseEntity<?> patchUpdateClass(
            @PathVariable("classId") Long updateClassroomId,
            @RequestBody @Valid AClassRequest classRequest
    ) throws CustomException {
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                classroomService.patchUpdate(updateClassroomId, classRequest)
            ), HttpStatus.OK);
    }
    // * softDelete an exists classroom.
    @DeleteMapping("{classId}")
    public ResponseEntity<?> softDeleteClassById(@PathVariable("classId") Long classId) throws CustomException {
        classroomService.softDeleteByClassId(classId);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Delete class successfully."
            ), HttpStatus.OK);
    }
    // * hardDelete an exists classroom.
    @DeleteMapping("/delete/{classId}")
    public ResponseEntity<?> hardDeleteClassById(@PathVariable("classId") Long classId) throws CustomException {
        classroomService.hardDeleteByClassId(classId);
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
        Page<Classroom> classes = classroomService.searchAllClassByClassNameToPages(keyword, limit, page, sort, order);
        Page<AClassResponse> classResponses = classroomService.entityAMap(classes);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                classResponses.getContent()
            ), HttpStatus.OK);
    }
}