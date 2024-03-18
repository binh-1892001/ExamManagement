package trainingmanagement.model.dto.response.teacher;

import lombok.*;
import trainingmanagement.model.enums.EQuestionLevel;
import trainingmanagement.model.enums.EQuestionType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TQuestionResponse {
    private Long questionId;
    private String contentQuestion;
    private EQuestionType typeQuestion;
    private EQuestionLevel levelQuestion;
    private String image;
}
