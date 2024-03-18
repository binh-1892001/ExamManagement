package trainingmanagement.model.dto.response.admin;

import lombok.*;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EOptionStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AOptionResponse {
    private Long optionId;
    private String contentOptions;
    private EOptionStatus isCorrect;
    private EActiveStatus status;
}
