package trainingmanagement.controller.teacher;

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
import trainingmanagement.model.dto.response.teacher.TClassResponse;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.ClassroomService;
import trainingmanagement.service.CommonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/teacher/classes")
@RequiredArgsConstructor
public class TClassController {
    private final CommonService commonService;
    private final ClassroomService classroomService;
    @GetMapping()
    public ResponseEntity<?> getAllClassesPage(
            @RequestParam(defaultValue = "5",name = "limit" ) int limit,
            @RequestParam(defaultValue = "0" ,name="page") int page,
            @RequestParam(defaultValue = "className", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    )throws CustomException {
        Pageable pageable;
        if(order.equals("asc")){
            pageable = PageRequest.of(page,limit, Sort.by(sort).ascending());
        }else {
            pageable = PageRequest.of(page,limit,Sort.by(sort).descending());
        }
        try {
            List<TClassResponse> TClassRespons = classroomService.teacherGetListClassrooms();
            Page<?> classroom = commonService.convertListToPages(pageable, TClassRespons);
            if (!classroom.isEmpty()) {
                return new ResponseEntity<>(
                    new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        classroom.getContent()
                    ), HttpStatus.OK);
            }
            throw new CustomException("Classes page is empty.");
        }catch (IllegalArgumentException illegalArgumentException){
            throw new CustomException("Classes pages out of range !!");
        }
    }
    @GetMapping("/{classId}")
    public ResponseEntity<?> getClassById(@PathVariable("classId") Long classId) throws CustomException{
        Optional<TClassResponse> classroom = classroomService.teacherGetClassById(classId);
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
    @GetMapping("/search")
    public ResponseEntity<?> searchClass(
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
            List<TClassResponse> TClassRespons = classroomService.teacherFindClassByName(keyword);
            Page<?> classrooms = commonService.convertListToPages(pageable, TClassRespons);
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
}
