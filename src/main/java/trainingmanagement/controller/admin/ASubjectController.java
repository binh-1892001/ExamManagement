package trainingmanagement.controller.admin;

import jakarta.validation.Valid;
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
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.request.admin.ASubjectRequest;
import trainingmanagement.model.dto.response.admin.ASubjectResponse;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.SubjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/subjects")
public class ASubjectController {
    private final CommonService commonService;
    private final SubjectService subjectService;

    // * Get all subjects to pages.
    @GetMapping
    public ResponseEntity<?> getAllSubjectToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "subjectName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        try {
            Pageable pageable;
            if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<ASubjectResponse> subjectResponses = subjectService.getAllSubjectResponsesToList(pageable);
            if (subjectResponses.getContent().isEmpty()) throw new CustomException("Classes page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            subjectResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    // * Get subject by id.
    @GetMapping("/{subjectId}")
    public ResponseEntity<?> getSubjectById(@PathVariable("subjectId") String subjectId) throws CustomException {
        try {
            Long id = Long.parseLong(subjectId);
            Optional<Subject> subject = subjectService.getById(id);
            if (subject.isEmpty())
                throw new CustomException("Subject is not exists.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            subject.get()
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * Create new subject.
    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody @Valid ASubjectRequest subjectRequest) {
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
            @PathVariable("subjectId") String updateSubjectId,
            @RequestBody @Valid ASubjectRequest subjectRequest
    ) throws CustomException {
        try {
            Long id = Long.parseLong(updateSubjectId);
            Subject subject = subjectService.patchUpdate(id, subjectRequest);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            subject
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * softDelete an existed subject.
    @DeleteMapping("/{subjectId}")
    public ResponseEntity<?> softDeleteSubjectById(@PathVariable("subjectId") String subjectId) throws CustomException {
        try {
            Long id = Long.parseLong(subjectId);
            subjectService.softDeleteById(id);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            "Delete Subject successfully."
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * hardDelete an existed subject.
    @DeleteMapping("/delete/{subjectId}")
    public ResponseEntity<?> hardDeleteSubjectById(@PathVariable("subjectId") String subjectId) throws CustomException {
        try {
            Long id = Long.parseLong(subjectId);
            subjectService.hardDeleteById(id);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            "Delete Subject successfully."
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    // * Find subject by subjectName.
    @GetMapping("/search")
    public ResponseEntity<?> searchAllSubjectToPages(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "subjectName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        try {
            Pageable pageable;
            if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<ASubjectResponse> subjectResponses = subjectService.findBySubjectName(keyword, pageable);
            if (subjectResponses.getContent().isEmpty()) throw new CustomException("Classes page is empty.");
            return new ResponseEntity<>(new ResponseWrapper<>(EHttpStatus.SUCCESS, HttpStatus.OK.value(), HttpStatus.OK.name(), subjectResponses.getContent()), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }
}
