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
public class TQuestionRequest {
    @Pattern(regexp = "^(?i)(ACTIVE|INACTIVE)$", message = "Chuỗi phải là 'ACTIVE' hoặc 'INACTIVE'")
    private String status;
    @NotNull(message = "Không được bỏ trống chỗ này nha!!")
    private Long testId;
    @NotEmpty(message = "Không được bỏ trống chỗ này nha!!")
    private String contentQuestion;
    @Pattern(regexp = "^(?i)(EASY|NORMAL|DIFFICULTY)$", message = "Chuỗi phải là 'EASY' hoặc 'NORMAL' hoặc 'DIFFICULTY' ")
    private String levelQuestion;
    @Pattern(regexp = "^(?i)(MULTIPLE|SINGLE)$", message = "Chuỗi phải là 'MULTIPLE' hoặc 'SINGLE'")
    private String typeQuestion;
    private String image;
}
