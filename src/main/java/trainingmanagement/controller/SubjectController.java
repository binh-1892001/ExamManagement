package trainingmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.model.dto.SubjectRequest;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.service.Subject.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;
    @GetMapping("")
    public ResponseEntity<?> findALl(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "nameSubject", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ){
        Pageable pageable;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        }
        Page<Subject> subjects = subjectService.getAll(pageable);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<Subject> add(@RequestBody SubjectRequest subjectRequest) {
        Subject subject = subjectService.add(subjectRequest);
        return new ResponseEntity<>(subject, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Subject> edit(
            @PathVariable("id") Long id,
            @RequestBody SubjectRequest productRequest
    ) {
        Subject subject = subjectService.edit(productRequest, id);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        subjectService.delete(id);
        return new ResponseEntity<>("Xóa môn học thành công",HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Subject>> searchProduct(@RequestParam(name = "name") String name) {
        List<Subject> subjects = subjectService.getByName(name);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }
}
