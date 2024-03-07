package trainingmanagement.model.dto.requestEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExamRequest {
    private String examName;
    private Boolean status;
    private Long subjectId;
}
