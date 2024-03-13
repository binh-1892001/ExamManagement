package trainingmanagement.controller.admin;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.Wrapper.ResponseWrapper;
import trainingmanagement.model.dto.request.SubjectRequest;
import trainingmanagement.model.dto.response.SubjectResponse;
import trainingmanagement.model.entity.Enum.EHttpStatus;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.Subject.SubjectService;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/subjects")
public class ASubjectController {
    private final CommonService commonService;
    private final SubjectService subjectService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // * Get all subjects to pages.
    @GetMapping
    public ResponseEntity<?> getAllSubjectToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "subjectName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException{
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<SubjectResponse> subjectResponses = subjectService.getAllSubjectResponsesToList();
            Page<?> subjects = commonService.convertListToPages(pageable, subjectResponses);
            if (!subjects.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                subjects.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Subjects page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Subjects page is out of range.");
        }
    }
    // * Get subject by id.
    @GetMapping("/{subjectId}")
    public ResponseEntity<?> getSubjectById(@PathVariable("subjectId") Long subjectId) throws CustomException{
        Optional<Subject> subject = subjectService.getById(subjectId);
        if(subject.isEmpty())
            throw new CustomException("Subject is not exists.");
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        subject.get()
                ), HttpStatus.OK);
    }
    // * Create new subject.
    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody SubjectRequest subjectRequest) {
        Subject subject = subjectService.save(subjectRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        subject
                ), HttpStatus.CREATED);
    }
    // * Update an existed subject.
    @PatchMapping("/{subjectId}")
    public ResponseEntity<?> pathUpdateSubject(
            @PathVariable("subjectId") Long updateSubjectId,
            @RequestBody SubjectRequest subjectRequest
    ) {
        Subject subject = subjectService.patchUpdate(updateSubjectId, subjectRequest);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        subject
                ), HttpStatus.OK);
    }
    // * Delete an existed subject.
    @DeleteMapping("/{subjectId}")
    public ResponseEntity<?> deleteSubjectById(@PathVariable("subjectId") Long subjectId) {
        subjectService.deleteById(subjectId);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Delete Subject successfully."
                ), HttpStatus.OK);
    }
    // * Find subject by subjectName.
    @GetMapping("/search")
    public ResponseEntity<?> searchAllSubjectToPages(
        @RequestParam(name = "keyword") String keyword,
        @RequestParam(defaultValue = "5", name = "limit") int limit,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "subjectName", name = "sort") String sort,
        @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException{
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<SubjectResponse> subjectResponses = subjectService.findBySubjectName(keyword);
            Page<?> subjects = commonService.convertListToPages(pageable, subjectResponses);
            if (!subjects.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                subjects.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Subjects page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Subjects page is out of range.");
        }
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<?> getAllSubjectByClassIdToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "subjectName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order,
            @PathVariable Long classId
    ) throws CustomException{
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<SubjectResponse> subjectResponses = subjectService.getAllByClassId(classId);
            Page<?> subjects = commonService.convertListToPages(pageable, subjectResponses);
            if (!subjects.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                subjects.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Subjects page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Subjects page is out of range.");
        }
    }
}
