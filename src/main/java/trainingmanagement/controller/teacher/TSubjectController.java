package trainingmanagement.controller.teacher;

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
import trainingmanagement.model.dto.response.admin.ASubjectResponse;
import trainingmanagement.model.dto.response.teacher.TSubjectResponse;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.SubjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/teacher/subjects")
public class TSubjectController {
    private final CommonService commonService;
    private final SubjectService subjectService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // * Get all subjects to pages.
    @GetMapping
    public ResponseEntity<?> getAllSubjectToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "subjectName", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "sortBy") String sortBy
    ) throws CustomException {
        try {
            Pageable pageable;
            if (sortBy.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
            else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
            Page<TSubjectResponse> examResponses = subjectService.getAllSubjectResponsesToListRoleTeacher(pageable);
            if (examResponses.getContent().isEmpty()) throw new CustomException("Classes page is empty.");
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            examResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    // * Get Subject by id.
    @GetMapping("/{subjectId}")
    public ResponseEntity<?> getSubjectById(@PathVariable("subjectId") String subjectId) throws CustomException {
        try {
            Long id = Long.parseLong(subjectId);
            Optional<TSubjectResponse> subject = subjectService.getSubjectResponsesByIdWithActiveStatus(id);
            if (subject.isPresent())
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                subject.get()
                        ), HttpStatus.OK);
            throw new CustomException("Subject is not exists.");
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
            Page<TSubjectResponse> subjectResponses = subjectService.findBySubjectNameRoleTeacher(keyword, pageable);
            if (subjectResponses.getContent().isEmpty()) throw new CustomException("Classes page is empty.");
            return new ResponseEntity<>(new ResponseWrapper<>(EHttpStatus.SUCCESS, HttpStatus.OK.value(), HttpStatus.OK.name(), subjectResponses.getContent()), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }
}