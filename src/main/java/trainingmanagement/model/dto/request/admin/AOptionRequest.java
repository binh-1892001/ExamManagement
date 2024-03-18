package trainingmanagement.model.dto.request.admin;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AOptionRequest {
    String contentOptions;
    Long questionId;
    String status;
}
