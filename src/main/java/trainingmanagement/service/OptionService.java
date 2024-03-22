package trainingmanagement.service;

import trainingmanagement.model.dto.request.admin.AOptionRequest;
import trainingmanagement.model.dto.request.teacher.TOptionRequest;
import trainingmanagement.model.dto.response.admin.AOptionResponse;
import trainingmanagement.model.dto.response.teacher.TOptionResponse;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import java.util.List;
import java.util.Optional;

public interface OptionService {
    List<Option> getAllToList();
    List<AOptionResponse> getAllOptionResponsesToList();
    Optional<Option> getById(Long optionId);
    List<AOptionResponse> findAllByQuestion(Question question);
    List<Option> getAllByQuestion(Question question);
    Option save(Option option);
    Option save(AOptionRequest optionRequest);
    Option save(TOptionRequest optionRequest);
    Option patchUpdateOption(Long optionId, AOptionRequest optionRequest);
    void deleteByQuestion(Question question);
    void deleteById(Long optionId);
    Option entityAMap(AOptionRequest optionRequest);
    Option entityTMap(TOptionRequest optionRequest);
    AOptionResponse entityAMap(Option option);
    TOptionResponse entityTMap(Option option);
}
