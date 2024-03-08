package trainingmanagement.model.dto.requestEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestQuestionPostQuiz {

    //dto thêm câu hỏi trắc nghiệm
    String contentQuestion;
    String levelQuestion;
    String typeQuestion;
    String image;
}
