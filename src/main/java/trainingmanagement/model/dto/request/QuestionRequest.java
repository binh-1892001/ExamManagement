package trainingmanagement.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionRequest {
    String contentQuestion;
    String levelQuestion;
    String typeQuestion;
    String image;
}
