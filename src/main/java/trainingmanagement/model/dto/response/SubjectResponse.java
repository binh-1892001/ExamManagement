package trainingmanagement.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SubjectResponse {
    private String subjectName;
    private String timeToStudy;
    private Boolean status;
}
