package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.request.admin.AOptionRequest;
import trainingmanagement.model.dto.request.teacher.TOptionRequest;
import trainingmanagement.model.dto.response.admin.AOptionResponse;
import trainingmanagement.model.dto.response.teacher.TOptionResponse;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EOptionStatus;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.repository.OptionRepository;
import trainingmanagement.repository.QuestionRepository;
import trainingmanagement.service.OptionService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionServiceImp implements OptionService {
    private final OptionRepository optionRepository;
    private final QuestionRepository questionRepository;

    @Override
    public List<Option> getAllToList() {
        return optionRepository.findAll();
    }

    @Override
    public List<AOptionResponse> getAllOptionResponsesToList() {
        return getAllToList().stream().map(this::entityAMap).toList();
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
    public Option save(AOptionRequest optionRequest) {
        return optionRepository.save(entityAMap((optionRequest)));
    }

    @Override
    public Option save(TOptionRequest optionRequest) {
        return optionRepository.save(entityTMap((optionRequest)));
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
        return options.stream().map(this::entityAMap).toList();
    }

    @Override
    public List<Option> getAllByQuestion(Question question) {
        return optionRepository.findAllByQuestion(question);
    }

    @Override
    public Option patchUpdateOption(Long optionId, AOptionRequest optionRequest) {
        Optional<Option> updateOption = optionRepository.findById(optionId);
        if(updateOption.isPresent()){
            Option option = updateOption.get();
            if(optionRequest.getOptionContent() != null)
                option.setOptionContent(optionRequest.getOptionContent());
            if(optionRequest.getQuestionId() != null){
                Question question = questionRepository.findById(optionRequest.getQuestionId()).orElse(null);
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

    //    *********************************************entityMap*********************************************
    @Override
    public Option entityAMap(AOptionRequest optionRequest) {
        EOptionStatus isCorrect = switch (optionRequest.getIsCorrect().toUpperCase()) {
            case "INCORRECT" -> EOptionStatus.INCORRECT;
            case "CORRECT" -> EOptionStatus.CORRECT;
            default -> null;
        };
        EActiveStatus status = switch (optionRequest.getStatus().toUpperCase()) {
            case "INACTIVE" -> EActiveStatus.INACTIVE;
            case "ACTIVE" -> EActiveStatus.ACTIVE;
            default -> null;
        };
        return Option.builder()
            .optionContent(optionRequest.getOptionContent())
            .question(questionRepository.findById(optionRequest.getQuestionId()).orElse(null))
            .isCorrect(isCorrect)
            .status(status)
            .build();
    }

    @Override
    public Option entityTMap(TOptionRequest optionRequest) {
        EOptionStatus isCorrect = switch (optionRequest.getIsCorrect().toUpperCase()) {
            case "INCORRECT" -> EOptionStatus.INCORRECT;
            case "CORRECT" -> EOptionStatus.CORRECT;
            default -> null;
        };
        EActiveStatus status = switch (optionRequest.getStatus().toUpperCase()) {
            case "INACTIVE" -> EActiveStatus.INACTIVE;
            case "ACTIVE" -> EActiveStatus.ACTIVE;
            default -> null;
        };
        return Option.builder()
                .optionContent(optionRequest.getContentOptions())
                .question(questionRepository.findById(optionRequest.getQuestionId()).orElse(null))
                .isCorrect(isCorrect)
                .status(status)
                .build();
    }

    @Override
    public AOptionResponse entityAMap(Option option) {
        return AOptionResponse.builder()
            .optionId(option.getId())
            .optionContent(option.getOptionContent())
            .isCorrect(option.getIsCorrect())
            .status(option.getStatus())
            .build();
    }

    @Override
    public TOptionResponse entityTMap(Option option) {
        return TOptionResponse.builder()
                .optionId(option.getId())
                .contentOptions(option.getOptionContent())
                .isCorrect(option.getIsCorrect())
                .status(option.getStatus())
                .build();
    }
}
