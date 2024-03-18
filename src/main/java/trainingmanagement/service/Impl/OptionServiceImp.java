package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.request.admin.AOptionRequest;
import trainingmanagement.model.dto.response.admin.AOptionResponse;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EOptionStatus;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.repository.OptionRepository;
import trainingmanagement.service.OptionService;
import trainingmanagement.service.QuestionService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionServiceImp implements OptionService {
    private final OptionRepository optionRepository;
    private final QuestionService questionService;
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
    public List<AOptionResponse> findAllByQuestion(Question question) {
        List<Option> options = optionRepository.findAllByQuestion(question);
        return options.stream().map(this::entityMap).toList();
    }

    @Override
    public Option patchUpdateOption(Long optionId, AOptionRequest optionRequest) {
        Optional<Option> updateOption = optionRepository.findById(optionId);
        if(updateOption.isPresent()){
            Option option = updateOption.get();
            if(optionRequest.getOptionContent() != null)
                option.setOptionContent(optionRequest.getOptionContent());
            if(optionRequest.getQuestionId() != null){
                Question question = questionService.getById(optionRequest.getQuestionId()).orElse(null);
                option.setQuestion(question);
            }
            if(optionRequest.getIsCorrect() != null){
                EOptionStatus isCorrect = switch (optionRequest.getIsCorrect().toUpperCase()) {
                    case "INCORRECT" -> EOptionStatus.INCORRECT;
                    case "CORRECT" -> EOptionStatus.CORRECT;
                    default -> null;
                };
                option.setIsCorrect(isCorrect);
            }
            if(optionRequest.getStatus() != null){
                EActiveStatus status = switch (optionRequest.getStatus().toUpperCase()) {
                    case "INACTIVE" -> EActiveStatus.INACTIVE;
                    case "ACTIVE" -> EActiveStatus.ACTIVE;
                    default -> null;
                };
                option.setStatus(status);
            }
            return optionRepository.save(option);
        }
        return null;
    }

    @Override
    public Option entityMap(AOptionRequest AOptionRequest) {
        EOptionStatus isCorrect = switch (AOptionRequest.getStatus().toUpperCase()) {
            case "INCORRECT" -> EOptionStatus.INCORRECT;
            case "CORRECT" -> EOptionStatus.CORRECT;
            default -> null;
        };
        EActiveStatus status = switch (AOptionRequest.getStatus().toUpperCase()) {
            case "INACTIVE" -> EActiveStatus.INACTIVE;
            case "ACTIVE" -> EActiveStatus.ACTIVE;
            default -> null;
        };
        return Option.builder()
            .optionContent(AOptionRequest.getOptionContent())
            .question(questionService.getById(AOptionRequest.getQuestionId()).orElse(null))
            .isCorrect(isCorrect)
            .status(status)
            .build();
    }

    @Override
    public AOptionResponse entityMap(Option option) {
        return AOptionResponse.builder()
            .optionId(option.getId())
            .optionContent(option.getOptionContent())
            .isCorrect(option.getIsCorrect())
            .status(option.getStatus())
            .build();
    }
}
