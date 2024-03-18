package trainingmanagement.service.Option;

import trainingmanagement.model.dto.request.admin.AOptionRequest;
import trainingmanagement.model.dto.response.admin.AOptionResponse;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import java.util.List;
import java.util.Optional;

public interface OptionService {
    List<Option> getAllToList();
    List<AOptionResponse> getAllOptionResponsesToList();
    Optional<Option> getById(Long optionId);
    List<AOptionResponse> findAllByQuestion(Question question);
    Option save(Option option);
    Option save(AOptionRequest AOptionRequest);
    void deleteById(Long optionId);
    Option patchUpdateOption(Long optionId, AOptionRequest AOptionRequest);
    Option entityMap(AOptionRequest AOptionRequest);
    AOptionResponse entityMap(Option option);
}
