package trainingmanagement.model.dto.admin.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OptionRequest {
    String contentOptions;
    Long questionId;
    String isCorrect;
}
