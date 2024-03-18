package trainingmanagement.model.dto.request.admin;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AOptionRequest {
    private String contentOptions;
    private String isCorrect;
    private String status;
    private Long questionId;
}
