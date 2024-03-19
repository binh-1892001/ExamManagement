package trainingmanagement.model.dto.response.teacher;

import lombok.*;
import trainingmanagement.model.enums.EActiveStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TOptionResponse {
    private Long optionId;
    private String contentOptions;
    private EActiveStatus status;
}
