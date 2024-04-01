package trainingmanagement.model.dto.response.teacher;

import lombok.*;
import trainingmanagement.model.entity.Subject;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TExamResponse {
        private Long examId;
        private String examName;
        private Subject subject;
}
