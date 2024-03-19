package trainingmanagement.model.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {
    @NotEmpty(message = "Không được bỏ trống chỗ này nha!!")
    private String username;
    @NotEmpty(message = "Không được bỏ trống chỗ này nha!!")
    private String password;
}
