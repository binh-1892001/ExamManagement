package trainingmanagement.model.dto.request.admin;

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
