package trainingmanagement.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionResponse {
    private Long questionId;
    private String contentQuestion;
    private String typeQuestion;
    private String levelQuestion;
    private String image;
    private String status;
}
