package trainingmanagement.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequest {
    @NotEmpty(message = "Không được bỏ trống chỗ này nha!!")
    private String fullName;
    @Size(min = 6, max = 100, message = "Số kí tự từ 6 -> 100")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Chứa kí tự đặc biệt rồi nhaaa!!")
    private String username;
    @NotEmpty(message = "Không được bỏ trống chỗ này nha!!")
    private String password;
    @Email(message = "email bạn chưa đúng định dạng")
    private String email;
    @Pattern(regexp = "^0[1-9]\\d{8}$", message = "Sai số rồi nhaaa, nhập lại đeeeee!!")
    private String phone;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "ngày tháng năm không đúng định dạng")
    private String dateOfBirth;
    private String avatar;
    @Pattern(regexp = "^(?i)(MALE|FEMALE)$", message = "Chuỗi phải là 'MALE' hoặc 'FEMALE'")
    private String gender;
}
