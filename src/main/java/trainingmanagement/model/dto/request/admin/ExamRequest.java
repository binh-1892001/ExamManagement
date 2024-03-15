package trainingmanagement.model.dto.request.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExamRequest {
    @NotEmpty(message = "Ten bai kiem tra khong duoc rong")
    private String examName;
    @Pattern(regexp = "^(?i)(ACTIVE|INACTIVE)$", message = "Chuỗi phải là 'ACTIVE' hoặc 'INACTIVE'")
    private String status;
    @NotNull(message = "Ma mon hoc khong  duoc rong")
    private Long subjectId;
}