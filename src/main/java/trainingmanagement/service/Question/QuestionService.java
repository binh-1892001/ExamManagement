package trainingmanagement.service.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.model.dto.requestEntity.RequestOptionPutQuiz;
import trainingmanagement.model.dto.requestEntity.RequestQuestionPostQuiz;
import trainingmanagement.model.dto.requestEntity.RequestQuestionPutQuiz;
import trainingmanagement.model.dto.responseEntity.ResponseQuestionQuiz;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;

public interface QuestionService {
    Page<ResponseQuestionQuiz> getAll(Pageable pageable);
    Question getById(Long id);
    Page<ResponseQuestionQuiz> findByName(String keyWord,Pageable pageable);
    Question save(Question question);
    void deleteById(Long id);
    ResponseQuestionQuiz displayQuestion(Question question);
    Question addQuestion(RequestQuestionPostQuiz questionQuiz);
    Question updateQuestion(RequestQuestionPutQuiz requestQuestionPutQuiz, Long idQuestion);

}
