package trainingmanagement.service.Option;

import trainingmanagement.model.dto.request.admin.OptionRequest;
import trainingmanagement.model.dto.response.admin.OptionResponse;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import java.util.List;
import java.util.Optional;

public interface OptionService {
    List<Option> getAllToList();
    List<OptionResponse> getAllOptionResponsesToList();
    Optional<Option> getById(Long optionId);
    List<OptionResponse> findAllByQuestion(Question question);
    Option save(Option option);
    Option save(OptionRequest optionRequest);
    void deleteById(Long optionId);
    Option patchUpdateOption(Long optionId, OptionRequest optionRequest);
    Option entityMap(OptionRequest optionRequest);
    OptionResponse entityMap(Option option);
}
