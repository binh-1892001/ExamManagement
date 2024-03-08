package trainingmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.model.base.AuditableEntity;
import trainingmanagement.model.dto.ClassroomRequest;
import trainingmanagement.model.dto.SubjectRequest;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Enum.EStatusClass;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.service.Classroom.ClassroomService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin/classroom")
public class ClassroomController {
    @Autowired
    private ClassroomService classroomService;
    @GetMapping("")
    public ResponseEntity<?> findALl(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "nameClass", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ){
        Pageable pageable;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        }
        Page<Classroom> classrooms = classroomService.getAll(pageable);
        return new ResponseEntity<>(classrooms, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<Classroom> add(@RequestBody ClassroomRequest classroomRequest) {
        Classroom classroom = classroomService.save(classroomRequest);
        return new ResponseEntity<>(classroom, HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> edit(
            @PathVariable("id") Long updateClassroomId,
            @RequestBody ClassroomRequest classroomRequest
    ) {
        classroomService.patchUpdate(updateClassroomId, classroomRequest);
        return new ResponseEntity<>("Update classroom", HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        classroomService.delete(id);
        return new ResponseEntity<>("Xóa môn học thành công",HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Classroom>> searchProduct(@RequestParam(name = "name") String name) {
        List<Classroom> classrooms = classroomService.searchByName(name);
        return new ResponseEntity<>(classrooms, HttpStatus.OK);
    }
}
