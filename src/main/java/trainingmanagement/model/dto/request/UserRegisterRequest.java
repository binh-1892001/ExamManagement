package trainingmanagement.model.dto.request;

import lombok.*;
import trainingmanagement.model.entity.Enum.ERoles;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRegisterRequest {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String avatar;
    private Boolean gender;
    private ERoles role;
}
