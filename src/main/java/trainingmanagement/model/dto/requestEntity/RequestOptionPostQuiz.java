package trainingmanagement.model.dto.requestEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestOptionPostQuiz {
    String contentOptions;
    Boolean status;
    Long questionId;
}
