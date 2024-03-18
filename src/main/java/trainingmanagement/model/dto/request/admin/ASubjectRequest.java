package trainingmanagement.model.dto.request.admin;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ASubjectRequest {
    private String subjectName;
    private String status;
}
