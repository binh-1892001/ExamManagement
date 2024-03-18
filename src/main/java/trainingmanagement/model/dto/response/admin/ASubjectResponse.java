package trainingmanagement.model.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.enums.EActiveStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ASubjectResponse {
    private String subjectName;
    private EActiveStatus status;
}
