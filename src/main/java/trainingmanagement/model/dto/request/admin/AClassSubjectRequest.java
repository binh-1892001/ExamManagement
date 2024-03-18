package trainingmanagement.model.dto.request.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AClassSubjectRequest {
    private Long classId;
    private Long subjectId;
}
