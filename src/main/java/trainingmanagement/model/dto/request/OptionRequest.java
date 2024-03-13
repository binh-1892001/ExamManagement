package trainingmanagement.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OptionRequest {
    String contentOptions;
    Long questionId;
    String status;
}
