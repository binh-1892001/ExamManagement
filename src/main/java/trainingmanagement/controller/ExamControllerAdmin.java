package trainingmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.model.dto.requestEntity.ExamRequest;
import trainingmanagement.model.entity.Exam;
import trainingmanagement.service.examService.ExamService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/admin/exam")
public class ExamControllerAdmin {
    @Autowired
    private ExamService examService;

    @GetMapping()
    public ResponseEntity<Page<Exam>> getAll(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "examName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "sortBy") String sortBy
    ) {
        Pageable pageable;
        if (sortBy.equals ( "asc" )) {
            pageable = PageRequest.of ( page, limit, Sort.by ( sort ).ascending () );
        } else {
            pageable = PageRequest.of ( page, limit, Sort.by ( sort ).descending () );
        }
        Page<Exam> exams = examService.getAll ( pageable );
        return new ResponseEntity<> ( exams, HttpStatus.OK );
    }

    @PostMapping("")
    public ResponseEntity<Exam> create(@RequestBody ExamRequest examRequest) {
        Exam examCreate = examService.add ( examRequest );
        return new ResponseEntity<> ( examCreate, HttpStatus.CREATED );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> update(@RequestBody ExamRequest examRequest, @PathVariable("id") Long id) {
        Exam examUpdate = examService.edit ( examRequest, id );
        return new ResponseEntity<> ( examUpdate, HttpStatus.OK );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getById(@PathVariable("id") Long id) {
        Exam exam = examService.findById ( id );
        if (exam == null) {
            throw new RuntimeException ( "Bài thi không tồn tại" );
        } else {
            return new ResponseEntity<> ( exam, HttpStatus.OK );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        examService.delete ( id );
        return new ResponseEntity<> ( "Đã xóa thành công", HttpStatus.OK );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Exam>> findByNameOrDateTime(
            @RequestParam(name = "examName") String query,
            @RequestParam(name = "date") Date createDate) {
        List<Exam> exams = examService.getByNameOrDateTime ( query, createDate );
        return new ResponseEntity<> ( exams, HttpStatus.OK );
    }
}
