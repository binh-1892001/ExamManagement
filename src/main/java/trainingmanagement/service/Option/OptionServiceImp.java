package trainingmanagement.service.Option;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.admin.request.OptionRequest;
import trainingmanagement.model.dto.admin.response.OptionResponse;
import trainingmanagement.model.entity.Enum.EOptionStatus;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.repository.OptionRepository;
import trainingmanagement.repository.QuestionRepository;
import trainingmanagement.service.Question.QuestionService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionServiceImp implements OptionService{
    private final OptionRepository optionRepository;
    private final QuestionRepository questionRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Option> getAllToList() {
        return optionRepository.findAll();
    }

    @Override
    public List<OptionResponse> getAllOptionResponsesToList() {
        return getAllToList().stream().map(this::entityMap).toList();
    }

    @Override
    public Optional<Option> getById(Long optionId) {
        return optionRepository.findById(optionId);
    }

    @Override
    public Option save(Option option) {
        return optionRepository.save(option);
    }

    @Override
    public Option save(OptionRequest optionRequest) {
        return optionRepository.save(entityMap((optionRequest)));
    }

    @Override
    public void deleteById(Long optionId) {
        optionRepository.deleteById(optionId);
    }

    @Override
    public void deleteByQuestion(Question question) {
        optionRepository.deleteByQuestion(question);
    }

    @Override
    public List<OptionResponse> findAllByQuestion(Question question) {
        List<Option> options = optionRepository.findAllByQuestion(question);
        return options.stream().map(this::entityMap).toList();
    }

    @Override
    public List<Option> getAllByQuestion(Question question) {
        return optionRepository.findAllByQuestion(question);
    }

    @Override
    public Option patchUpdateOption(Long optionId, OptionRequest optionRequest) {
        Optional<Option> updateOption = optionRepository.findById(optionId);
        if(updateOption.isPresent()){
            Option option = updateOption.get();
            if(optionRequest.getContentOptions() != null)
                option.setContentOptions(optionRequest.getContentOptions());
            if(optionRequest.getQuestionId() != null){
                Question question = questionRepository.findById(optionRequest.getQuestionId()).orElse(null);
                option.setQuestion(question);
            }
            if(optionRequest.getIsCorrect() != null){
                EOptionStatus activeStatus = switch (optionRequest.getIsCorrect().toUpperCase()) {
                    case "INCORRECT" -> EOptionStatus.INCORRECT;
                    case "CORRECT" -> EOptionStatus.CORRECT;
                    default -> null;
                };
                option.setIsCorrect(activeStatus);
            }
            return optionRepository.save(option);
        }
        return null;
    }

    @Override
    public Option entityMap(OptionRequest optionRequest) {
        EOptionStatus activeStatus = switch (optionRequest.getIsCorrect().toUpperCase()) {
            case "INCORRECT" -> EOptionStatus.INCORRECT;
            case "CORRECT" -> EOptionStatus.CORRECT;
            default -> null;
        };
        return Option.builder()
            .contentOptions(optionRequest.getContentOptions())
            .question(questionRepository.findById(optionRequest.getQuestionId()).orElse(null))
            .isCorrect(activeStatus)
            .build();
    }

    @Override
    public OptionResponse entityMap(Option option) {
        return OptionResponse.builder()
            .optionId(option.getId())
            .contentOptions(option.getContentOptions())
            .isCorrect(option.getIsCorrect().name())
            .build();
    }
}
