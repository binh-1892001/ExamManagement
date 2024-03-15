package trainingmanagement.model.dto.response.admin;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OptionResponse {
    private Long optionId;
    private String contentOptions;
    private String status;
}
