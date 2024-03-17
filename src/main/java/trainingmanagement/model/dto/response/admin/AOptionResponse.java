package trainingmanagement.model.dto.response.admin;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AOptionResponse {
    private Long optionId;
    private String contentOptions;
    private String isCorrect;
}
