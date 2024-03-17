package trainingmanagement.service.Option;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.request.admin.AOptionRequest;
import trainingmanagement.model.dto.response.admin.AOptionResponse;
import trainingmanagement.model.entity.Enum.EOptionStatus;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.repository.OptionRepository;
import trainingmanagement.repository.QuestionRepository;

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
    public List<AOptionResponse> getAllOptionResponsesToList() {
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
    public Option save(AOptionRequest AOptionRequest) {
        return optionRepository.save(entityMap((AOptionRequest)));
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
    public List<AOptionResponse> findAllByQuestion(Question question) {
        List<Option> options = optionRepository.findAllByQuestion(question);
        return options.stream().map(this::entityMap).toList();
    }

    @Override
    public List<Option> getAllByQuestion(Question question) {
        return optionRepository.findAllByQuestion(question);
    }

    @Override
    public Option patchUpdateOption(Long optionId, AOptionRequest AOptionRequest) {
        Optional<Option> updateOption = optionRepository.findById(optionId);
        if(updateOption.isPresent()){
            Option option = updateOption.get();
            if(AOptionRequest.getContentOptions() != null)
                option.setContentOptions(AOptionRequest.getContentOptions());
            if(AOptionRequest.getQuestionId() != null){
                Question question = questionRepository.findById(AOptionRequest.getQuestionId()).orElse(null);
                option.setQuestion(question);
            }
            if(AOptionRequest.getIsCorrect() != null){
                EOptionStatus activeStatus = switch (AOptionRequest.getIsCorrect().toUpperCase()) {
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
    public Option entityMap(AOptionRequest AOptionRequest) {
        EOptionStatus activeStatus = switch (AOptionRequest.getIsCorrect().toUpperCase()) {
            case "INCORRECT" -> EOptionStatus.INCORRECT;
            case "CORRECT" -> EOptionStatus.CORRECT;
            default -> null;
        };
        return Option.builder()
            .contentOptions(AOptionRequest.getContentOptions())
            .question(questionRepository.findById(AOptionRequest.getQuestionId()).orElse(null))
            .isCorrect(activeStatus)
            .build();
    }

    @Override
    public AOptionResponse entityMap(Option option) {
        return AOptionResponse.builder()
            .optionId(option.getId())
            .contentOptions(option.getContentOptions())
            .isCorrect(option.getIsCorrect().name())
            .build();
    }
}
