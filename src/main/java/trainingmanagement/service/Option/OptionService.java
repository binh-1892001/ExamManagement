package trainingmanagement.service.Option;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.model.dto.requestEntity.RequestOptionPostQuiz;
import trainingmanagement.model.dto.requestEntity.RequestOptionPutQuiz;
import trainingmanagement.model.dto.responseEntity.ResponseOptionQuiz;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;

import java.util.List;

public interface OptionService {
    Page<Option> getAll(Pageable pageable);
    Option getById(Long id);
    Option save(Option option);
    void deleteById(Long id);
    List<ResponseOptionQuiz> findAllByQuestion(Question question);
    ResponseOptionQuiz displayOption(Option option);
    Option addOption(RequestOptionPostQuiz requestOptionQuiz);
    Option updateOption(RequestOptionPutQuiz requestOptionPutQuiz,Long idOption);
}
