package trainingmanagement.service.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.requestEntity.RequestOptionPostQuiz;
import trainingmanagement.model.dto.requestEntity.RequestOptionPutQuiz;
import trainingmanagement.model.dto.responseEntity.ResponseOptionQuiz;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.repository.OptionRepository;
import trainingmanagement.repository.QuestionRepository;
import trainingmanagement.service.Question.QuestionService;

import java.util.List;

@Service
public class OptionServiceImp implements OptionService{
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private QuestionService questionService;
    @Override
    public Page<Option> getAll(Pageable pageable) {
        return optionRepository.findAll(pageable);
    }

    @Override
    public Option getById(Long id) {
        return optionRepository.findById(id).orElse(null);
    }

    @Override
    public Option save(Option option) {
        return optionRepository.save(option);
    }

    @Override
    public void deleteById(Long id) {
        optionRepository.deleteById(id);
    }

    @Override
    public List<ResponseOptionQuiz> findAllByQuestion(Question question) {
        List<Option> options = optionRepository.findAllByQuestion(question);
        return options.stream().map(this::displayOption).toList();
    }

    @Override
    public ResponseOptionQuiz displayOption(Option option) {
        return ResponseOptionQuiz.builder()
                .id(option.getId())
                .contentOptions(option.getContentOptions())
                .status(option.getStatus())
                .build();
    }

    @Override
    public Option addOption(RequestOptionPostQuiz requestOptionQuiz) {
        Option option = new Option();
        option.setContentOptions(requestOptionQuiz.getContentOptions());
        option.setStatus(requestOptionQuiz.getStatus());
        option.setQuestion(questionService.getById(requestOptionQuiz.getQuestionId()));
        return optionRepository.save(option);
    }

    @Override
    public Option updateOption(RequestOptionPutQuiz requestOptionPutQuiz,Long idOption) {
        Option option = getById(idOption);
        option.setContentOptions(requestOptionPutQuiz.getContentOptions());
        option.setStatus(requestOptionPutQuiz.getStatus());
        return optionRepository.save(option);
    }


}
