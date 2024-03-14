package trainingmanagement.model.dto.admin.request;

import lombok.*;
import trainingmanagement.model.entity.Enum.EActiveStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SubjectRequest {
    private String subjectName;
    private String timeToStudy;
    private Integer time;
    private String eActiveStatus;
}
