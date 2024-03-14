package trainingmanagement.model.dto.teacher.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionResponseTeacher {
    private Long questionId;
    private String contentQuestion;
    private String typeQuestion;
    private String levelQuestion;
    private String image;
}
