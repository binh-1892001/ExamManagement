package trainingmanagement.model.dto.requestEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestQuestionPutQuiz {

    //dto sửa câu hỏi trắc nghiệm
    String contentQuestion;
    String levelQuestion;
    String typeQuestion;
    String image;
}
