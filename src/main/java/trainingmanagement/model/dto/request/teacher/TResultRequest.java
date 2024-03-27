package trainingmanagement.model.dto.request.teacher;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TResultRequest {
    @NotNull(message = "Không được bỏ trống chỗ này nha!!")
    private Long studentId;
    @NotNull(message = "Không được bỏ trống chỗ này nha!!")
    private Long testId;
    @Pattern(regexp = "^(?i)(ACTIVE|INACTIVE)$", message = "String value must be \"INACTIVE/ACTIVE\"")
    private String status;
    private Integer examTimes;
    private Double mark;
}
