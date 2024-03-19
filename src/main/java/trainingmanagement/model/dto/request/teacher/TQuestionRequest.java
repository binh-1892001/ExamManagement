package trainingmanagement.model.dto.request.teacher;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TQuestionRequest {
    private String contentQuestion;
    private String levelQuestion;
    private String typeQuestion;
    private String image;
}
