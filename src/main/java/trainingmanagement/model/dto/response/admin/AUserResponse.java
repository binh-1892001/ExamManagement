package trainingmanagement.model.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EGender;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AUserResponse {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    private String avatar;
    private EGender gender;
    private EActiveStatus status;
}
