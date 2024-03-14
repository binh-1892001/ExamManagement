package trainingmanagement.model.dto.admin.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.entity.Subject;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExamResponse {
    private Long examId;
    private String examName;
    private String status;
    private Subject subject;
}
