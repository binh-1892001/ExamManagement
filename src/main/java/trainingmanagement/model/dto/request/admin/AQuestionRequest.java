package trainingmanagement.model.dto.request.admin;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AQuestionRequest {
    private String contentQuestion;
    private String levelQuestion;
    private String typeQuestion;
    private String image;
    private Long testId;
}
