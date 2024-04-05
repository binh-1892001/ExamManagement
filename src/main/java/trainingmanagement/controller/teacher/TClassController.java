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
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "className", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        try {
            Pageable pageable;
            if (order.equals("asc")) {
                pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            } else {
                pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            }
            Page<TClassResponse> classResponses = classroomService.getTAllToList(pageable);
            if (classResponses.getContent().isEmpty()) throw new CustomException("Classes page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            classResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    @GetMapping("/{classId}")
    public ResponseEntity<?> getClassById(@PathVariable("classId") String classId) throws CustomException {
        try {
            Long id = Long.parseLong(classId);
            Optional<TClassResponse> classroom = classroomService.getTClassById(id);
            if (classroom.isPresent())
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                classroom.get()
                        ), HttpStatus.OK);
            throw new CustomException("Class is not exists.");
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchClass(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "className", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        try {
            Pageable pageable;
            if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<TClassResponse> classResponses = classroomService.findTClassByClassName(keyword, pageable);
            if (classResponses.getContent().isEmpty()) throw new CustomException("Classes page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            classResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }


}
