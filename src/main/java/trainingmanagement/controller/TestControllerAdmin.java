package trainingmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.model.dto.request.TestRequest;
import trainingmanagement.model.entity.Test;
import trainingmanagement.service.Test.TestService;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/test")
public class TestControllerAdmin {
    @Autowired
    private TestService testService;

    @GetMapping()
    public ResponseEntity<Page<Test>> getAll(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "nameTest", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "sortBy") String sortBy
    ) {
        Pageable pageable;
        if (sortBy.equals ( "asc" )) {
            pageable = PageRequest.of ( page, limit, Sort.by ( sort ).ascending () );
        } else {
            pageable = PageRequest.of ( page, limit, Sort.by ( sort ).descending () );
        }
        Page<Test> test = testService.getAll ( pageable );
        return new ResponseEntity<> ( test, HttpStatus.OK );
    }

    @PostMapping("")
    public ResponseEntity<Test> create(@RequestBody TestRequest testRequest) {
        Test testCreate = testService.add ( testRequest );
        return new ResponseEntity<> ( testCreate, HttpStatus.CREATED );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Test> update(@RequestBody TestRequest testRequest, @PathVariable("id") Long id) {
        Test testUpdate = testService.edit ( testRequest, id );
        return new ResponseEntity<> ( testUpdate, HttpStatus.OK );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Test> getById(@PathVariable("id") Long id) {
        Test test = testService.findById ( id );
        if (test == null) {
            throw new RuntimeException ( "De thi không tồn tại" );
        } else {
            return new ResponseEntity<> ( test, HttpStatus.OK );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        testService.delete ( id );
        return new ResponseEntity<> ( "Đã xóa thành công", HttpStatus.OK );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Test>> findByNameTest(
            @RequestParam(name = "nameTest") String query) {
        List<Test> test = testService.getByNameTest ( query);
        return new ResponseEntity<> ( test, HttpStatus.OK );
    }
}
