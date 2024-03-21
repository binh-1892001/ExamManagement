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
import trainingmanagement.model.dto.request.admin.AOptionRequest;
import trainingmanagement.model.dto.response.admin.AOptionResponse;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.OptionService;
import trainingmanagement.service.QuestionService;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/options")
public class AOptionController {
    private final OptionService optionService;
    private final QuestionService questionService;
    private final CommonService commonService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // * Get all options to pages.
    @GetMapping
    public ResponseEntity<?> getAllQuestionsToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "contentOptions", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<AOptionResponse> optionResponses = optionService.getAllOptionResponsesToList();
            Page<?> options = commonService.convertListToPages(pageable, optionResponses);
            if (!options.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                options.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Options page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Options page is out of range.");
        }
    }
    // * Get option by id.
    @GetMapping("/{optionId}")
    public ResponseEntity<?> getOptionById(@PathVariable("optionId") Long optionId) throws CustomException{
        Optional<Option> option = optionService.getById(optionId);
        if(option.isPresent())
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            option.get()
                    ), HttpStatus.OK);
        throw new CustomException("Option is not exists.");
    }
    // * Get all options by questionId to list.
    @GetMapping("/question/{questionId}")
    public ResponseEntity<?> getAllOptionsByQuestion(@PathVariable("questionId") Long questionId) throws CustomException {
        Optional<Question> question = questionService.getById(questionId);
        if(question.isPresent()){
            List<AOptionResponse> options = optionService.findAllByQuestion(question.get());
            return new ResponseEntity<>(
                new ResponseWrapper<>(
                    EHttpStatus.SUCCESS,
                    HttpStatus.OK.value(),
                    HttpStatus.OK.name(),
                    options
                ), HttpStatus.OK);
        }
        throw new CustomException("Question is not exists.");
    }
    // * Create new option.
    @PostMapping
    public ResponseEntity<?> createNewOption(@RequestBody @Valid AOptionRequest optionRequest) {
        Option option = optionService.save(optionRequest);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                option
            ), HttpStatus.CREATED);
    }
    // * Patch update option by id.
    @PatchMapping("/{optionId}")
    public ResponseEntity<?> patchUpdateOption(
            @PathVariable("optionId") Long optionId,
            @RequestBody @Valid AOptionRequest optionRequest) {
        Option updatedOption = optionService.patchUpdateOption(optionId, optionRequest);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                updatedOption
            ), HttpStatus.OK);
    }
    // * Delete option by id.
    @DeleteMapping("/{optionId}")
    public ResponseEntity<?> deleteById(@PathVariable Long optionId) throws CustomException{
        Optional<Option> deletedOption = optionService.getById(optionId);
        if(deletedOption.isPresent()){
            optionService.deleteById(optionId);
            return new ResponseEntity<>(
                new ResponseWrapper<>(
                    EHttpStatus.SUCCESS,
                    HttpStatus.OK.value(),
                    HttpStatus.OK.name(),
                    "Delete option successfully."
                ), HttpStatus.OK);
        }
        // ? Xử lý Exception cần tìm được Option theo id trước khi xoá trong Controller.
        throw new CustomException("Option is not exists.");
    }
}
