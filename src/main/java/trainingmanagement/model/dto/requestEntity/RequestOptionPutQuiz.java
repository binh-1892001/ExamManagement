package trainingmanagement.model.dto.requestEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestOptionPutQuiz {
    String contentOptions;
    Boolean status;
}
