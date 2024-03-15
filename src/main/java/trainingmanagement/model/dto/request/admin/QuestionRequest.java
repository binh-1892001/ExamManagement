package trainingmanagement.model.dto.request.admin;

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
