package trainingmanagement.model.dto.auth;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequest {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String avatar;
    private String gender;
}
