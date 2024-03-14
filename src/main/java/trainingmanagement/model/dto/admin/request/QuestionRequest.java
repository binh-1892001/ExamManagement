package trainingmanagement.model.dto.admin.request;

import lombok.*;
import org.hibernate.mapping.List;
import trainingmanagement.model.entity.Option;

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
    List optionsList;
}
