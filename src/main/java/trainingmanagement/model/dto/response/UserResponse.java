package trainingmanagement.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    private String avatar;
    private String gender;
    private String status;
}
