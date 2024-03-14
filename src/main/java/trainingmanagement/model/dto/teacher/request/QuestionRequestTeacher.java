package trainingmanagement.model.dto.teacher.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionRequestTeacher {
    private String contentQuestion;
    private String levelQuestion;
    private String typeQuestion;
    private String image;
}
