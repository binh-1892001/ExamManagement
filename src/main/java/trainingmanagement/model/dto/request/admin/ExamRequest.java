package trainingmanagement.model.dto.request.admin;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExamRequest {
    private String examName;
    private String status;
    private Long subjectId;
}