package trainingmanagement.model.dto.requestEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestOptionPutQuiz {

    //dto sửa option trắc nghiệm
    String contentOptions;
    Boolean status;
}
