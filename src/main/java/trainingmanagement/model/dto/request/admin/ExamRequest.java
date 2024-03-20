package trainingmanagement.model.dto.request.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExamRequest {
    @NotEmpty(message = "Exam name must not be Null.")
    private String examName;
    @Pattern(regexp = "^(?i)(ACTIVE|INACTIVE)$", message = "String value must be \"INACTIVE/ACTIVE\"")
    private String status;
    private Long subjectId;
}