package trainingmanagement.model.dto.request.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AClassRequest {
    @NotEmpty(message = "Ten lop khong nhan null")
    private String className;
    @Pattern(regexp = "^(?i)(FINISH|NEW|OJT)$", message = "Chuỗi phải là 'NEW' hoặc 'FINISH' hoặc 'OJT'")
    private String classStatus;
}