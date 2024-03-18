package trainingmanagement.model.dto.response.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.entity.Subject;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TExamResponse {
        private Long examId;
        private String examName;
        private Subject subject;
}
