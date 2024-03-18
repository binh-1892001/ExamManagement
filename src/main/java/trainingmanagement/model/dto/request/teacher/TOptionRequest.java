package trainingmanagement.model.dto.request.teacher;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TOptionRequest {
    private String contentOptions;
    private Long questionId;
    private String status;
}
