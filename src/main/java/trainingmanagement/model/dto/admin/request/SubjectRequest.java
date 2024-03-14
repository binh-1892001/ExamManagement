package trainingmanagement.model.dto.admin.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SubjectRequest {
    private String subjectName;
    private String timeToStudy;
    private Integer time;
    private Boolean status;
}
