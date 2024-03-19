package trainingmanagement.model.dto.request.admin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AUserClassRequest {
    @NotNull(message = "Không được bỏ trống chỗ này nha!!")
    private Long classId;
    @NotNull(message = "Không được bỏ trống chỗ này nha!!")
    private Long userId;
}
