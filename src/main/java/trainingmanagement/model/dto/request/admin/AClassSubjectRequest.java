package trainingmanagement.model.dto.request.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AClassSubjectRequest {
    @NotEmpty(message = "Not empty!")
    @NotNull(message = "Not null!")
    private Long classId;
    @NotEmpty(message = "Not empty!")
    @NotNull(message = "Not null!")
    private Long subjectId;
}
