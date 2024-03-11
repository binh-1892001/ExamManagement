package trainingmanagement.model.dto.requestEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestQuestionPutQuiz {
    String contentQuestion;
    String levelQuestion;
    String typeQuestion;
    String image;
}
