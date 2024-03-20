package trainingmanagement.model.dto.request.teacher;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TOptionRequest {
    @NotEmpty(message = "Không được bỏ trống chỗ này nha!!")
    private String contentOptions;
    @NotNull(message = "Không được bỏ trống chỗ này nha!!")
    private Long questionId;
    @Pattern(regexp = "^(?i)(ACTIVE|INACTIVE)$", message = "Chuỗi phải là 'ACTIVE' hoặc 'INACTIVE'")
    private String status;
}
