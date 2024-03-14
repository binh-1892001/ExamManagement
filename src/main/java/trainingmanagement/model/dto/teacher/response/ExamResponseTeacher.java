package trainingmanagement.model.dto.teacher.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.entity.Subject;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExamResponseTeacher {
        private Long examId;
        private String examName;
        private Subject subject;
}
