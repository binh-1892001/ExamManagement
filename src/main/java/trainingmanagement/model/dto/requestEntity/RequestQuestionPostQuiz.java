package trainingmanagement.model.dto.requestEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestQuestionPostQuiz {
    String contentQuestion;
    String levelQuestion;
    String typeQuestion;
    String image;
}
