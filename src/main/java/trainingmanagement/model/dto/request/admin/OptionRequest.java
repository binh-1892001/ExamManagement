package trainingmanagement.model.dto.request.admin;

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
