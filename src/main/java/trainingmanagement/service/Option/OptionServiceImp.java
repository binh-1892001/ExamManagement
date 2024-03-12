package trainingmanagement.service.Option;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.request.OptionRequest;
import trainingmanagement.model.dto.response.OptionResponse;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.ELevelQuestion;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.repository.OptionRepository;
import trainingmanagement.service.Question.QuestionService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionServiceImp implements OptionService{
    private final OptionRepository optionRepository;
    private final QuestionService questionService;
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
    public List<OptionResponse> findAllByQuestion(Question question) {
        List<Option> options = optionRepository.findAllByQuestion(question);
        return options.stream().map(this::entityMap).toList();
    }

    @Override
    public Option patchUpdateOption(Long optionId, OptionRequest optionRequest) {
        Optional<Option> updateOption = optionRepository.findById(optionId);
        if(updateOption.isPresent()){
            Option option = updateOption.get();
            if(optionRequest.getContentOptions() != null)
                option.setContentOptions(optionRequest.getContentOptions());
            if(optionRequest.getQuestionId() != null){
                Question question = questionService.getById(optionRequest.getQuestionId()).orElse(null);
                option.setQuestion(question);
            }
            if(optionRequest.getStatus() != null){
                EActiveStatus activeStatus = switch (optionRequest.getStatus()) {
                    case "INACTIVE" -> EActiveStatus.INACTIVE;
                    case "ACTIVE" -> EActiveStatus.ACTIVE;
                    default -> null;
                };
                option.setStatus(activeStatus);
            }
        }
        return null;
    }

    @Override
    public Option entityMap(OptionRequest optionRequest) {
        EActiveStatus activeStatus = switch (optionRequest.getStatus()) {
            case "INACTIVE" -> EActiveStatus.INACTIVE;
            case "ACTIVE" -> EActiveStatus.ACTIVE;
            default -> null;
        };
        return Option.builder()
            .contentOptions(optionRequest.getContentOptions())
            .question(questionService.getById(optionRequest.getQuestionId()).orElse(null))
            .status(activeStatus)
            .build();
    }

    @Override
    public OptionResponse entityMap(Option option) {
        return OptionResponse.builder()
            .optionId(option.getId())
            .contentOptions(option.getContentOptions())
            .status(option.getStatus().name())
            .build();
    }
}
