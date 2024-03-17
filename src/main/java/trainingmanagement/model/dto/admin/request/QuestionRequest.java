package trainingmanagement.model.dto.admin.request;

import lombok.*;
import trainingmanagement.model.entity.Option;

import java.util.List;

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
    Long testId;
}
