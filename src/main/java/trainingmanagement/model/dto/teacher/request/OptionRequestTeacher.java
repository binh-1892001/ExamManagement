package trainingmanagement.model.dto.teacher.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OptionRequestTeacher {
    private String contentOptions;
    private Long questionId;
    private String status;
}
