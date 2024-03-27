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
    @NotNull(message = "Not null!!")
    private Long classId;
    @NotNull(message = "Not null!!")
    private Long userId;
}
