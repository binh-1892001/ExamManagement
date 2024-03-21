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
    @Pattern(regexp = "^(?i)(CORRECT|INCORRECT)$", message = "Chuỗi phải là 'CORRECT' hoặc 'INCORRECT'")
    private String isCorrect;
    @NotEmpty(message = "Không được bỏ trống chỗ này nha!!")
    private String contentOptions;
    @NotNull(message = "Không được bỏ trống chỗ này nha!!")
    private Long questionId;
    @Pattern(regexp = "^(?i)(ACTIVE|INACTIVE)$", message = "Chuỗi phải là 'ACTIVE' hoặc 'INACTIVE'")
    private String status;
}
