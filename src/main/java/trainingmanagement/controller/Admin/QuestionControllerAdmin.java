package trainingmanagement.controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.model.dto.requestEntity.RequestQuestionPostQuiz;
import trainingmanagement.model.dto.requestEntity.RequestQuestionPutQuiz;
import trainingmanagement.model.dto.responseEntity.Response;
import trainingmanagement.model.dto.responseEntity.ResponseQuestionQuiz;
import trainingmanagement.model.entity.Enum.EStatusCode;
import trainingmanagement.model.entity.Question;
import trainingmanagement.service.Question.QuestionService;

@RestController
@RequestMapping("/v1/admin/question")
public class QuestionControllerAdmin {
    @Autowired
    private QuestionService questionService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort));
        Page<ResponseQuestionQuiz> questions = questionService.getAll(pageable);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam String keyWord,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort
    ) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort));
        Page<ResponseQuestionQuiz> questions = questionService.findByName(keyWord,pageable);
        return new ResponseEntity<>(
                new Response<>(
                        EStatusCode.SUCCESS,
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        questions.getContent()
                ), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody RequestQuestionPostQuiz requestQuestionQuiz) {
        Question question = questionService.addQuestion(requestQuestionQuiz);
        return new ResponseEntity<>(
                new Response<>(
                        EStatusCode.SUCCESS,
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        question
                ), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody RequestQuestionPutQuiz requestQuestionPutQuiz, @PathVariable Long id) {
        Question questionUpdate = questionService.updateQuestion(requestQuestionPutQuiz,id);
        return new ResponseEntity<>(
                new Response<>(
                        EStatusCode.SUCCESS,
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        questionUpdate
                ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        questionService.deleteById(id);
        return new ResponseEntity<>(
                new Response<>(
                        EStatusCode.SUCCESS,
                        HttpStatus.OK.name(),
                        HttpStatus.OK.value(),
                        "Delete complete"
                ), HttpStatus.OK);
    }

}
