package trainingmanagement.model.dto.request.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AClassRequest {
    @NotEmpty(message = "Class name must not be Null.")
    private String className;
    @Pattern(regexp = "^(?i)(NEW|OJT/FINISH)$", message = "String value must be \"NEW/OJT/FINISH\"")
    private String classStatus;
}