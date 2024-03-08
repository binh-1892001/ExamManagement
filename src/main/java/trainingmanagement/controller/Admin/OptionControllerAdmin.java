package trainingmanagement.controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.model.dto.requestEntity.RequestOptionPostQuiz;
import trainingmanagement.model.dto.requestEntity.RequestOptionPutQuiz;
import trainingmanagement.model.dto.responseEntity.Response;
import trainingmanagement.model.dto.responseEntity.ResponseOptionQuiz;
import trainingmanagement.model.entity.Enum.EStatusCode;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.service.Option.OptionService;
import trainingmanagement.service.Question.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/question")
public class OptionControllerAdmin {
    @Autowired
    private OptionService optionService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getAll(@PathVariable Long questionId) {
        Question question = questionService.getById(questionId);
        List<ResponseOptionQuiz> options = optionService.findAllByQuestion(question);
        return new ResponseEntity<>(
                new Response<>(
                        EStatusCode.SUCCESS,
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        options
                ), HttpStatus.OK);
    }

    @PostMapping("/addOption")
    public ResponseEntity<?> add(@RequestBody RequestOptionPostQuiz requestOptionQuiz) {
        Option option = optionService.addOption(requestOptionQuiz);
        return new ResponseEntity<>(
                new Response<>(
                        EStatusCode.SUCCESS,
                        HttpStatus.CREATED.name(),
                        HttpStatus.CREATED.value(),
                        option
                ), HttpStatus.CREATED);
    }

    @PatchMapping("/updateOption/{id}")
    public ResponseEntity<?> update(@RequestBody RequestOptionPutQuiz requestOptionPutQuiz, @PathVariable Long id) {
        Option optionUpdate = optionService.updateOption(requestOptionPutQuiz,id);
        return new ResponseEntity<>(
                new Response<>(
                        EStatusCode.SUCCESS,
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        optionUpdate
                ), HttpStatus.OK);
    }

    @DeleteMapping("/deleteOption/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        optionService.deleteById(id);
        return new ResponseEntity<>(
                new Response<>(
                        EStatusCode.SUCCESS,
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        "Delete complete"
                ), HttpStatus.OK);
    }
}
