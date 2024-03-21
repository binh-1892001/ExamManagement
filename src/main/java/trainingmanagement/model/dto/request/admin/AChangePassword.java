package trainingmanagement.model.dto.request.admin;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AChangePassword {
    @NotEmpty(message = "Old Password must not be empty")
    private String oldPassword;
    @NotEmpty(message = "Old Password must not be empty")
    private String newPassword;
    @NotEmpty(message = "Confirm Password must not be empty")
    private String confirmPassword;
}
