package trainingmanagement.model.dto.response.teacher;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TOptionResponse {
    private Long optionId;
    private String contentOptions;
    private String status;
}
