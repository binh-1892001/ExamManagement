package trainingmanagement.model.dto.requestEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestOptionPostQuiz {

    // dto thêm option trắc nghiệm
    String contentOptions;
    Boolean status;
    Long questionId;
}
