/**
 * * Created by Nguyễn Hồng Quân.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto response dùng cho admin.
 * * Dùng để ReadData cho User Entity.
 * @param userId: Id của người dùng (User).
 * @param fullName: tên đầy đủ của người dùng.
 * @param username: nickname của người dùng.
 * @param password: mật khẩu của người dùng (có thể nên xoá bỏ trường này đi).
 * @param email: email của người dùng.
 * @param phone: số điện thoại của người dùng.
 * @param dateOfBirth: ngày sinh của người dùng.
 * @param avatar: ảnh đại diện của người dùng.
 * @param gender: giới tính của người dùng.
 * @param status: dùng để xoá mềm cho Entity.
 * @param createdDate: ngày tạo bản ghi.
 * @param modifyDate: ngày sửa đổi bản ghi gần nhất.
 * @param createdBy: thông tin user tạo bản ghi.
 * @param modifyBy: thông tin user sửa đổi bản ghi gần nhất.
 * @author: Nguyễn Hồng Quân.
 * @since: 18/3/2024.
 * */

package trainingmanagement.model.dto.response.admin;

import lombok.*;
import trainingmanagement.model.entity.Role;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EGender;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonFormat;
import trainingmanagement.model.enums.ERoleName;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AUserResponse {
    private Long userId;
    private String fullName;
    private String username;
    private String avatar;
    private String email;
    private String phone;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private EGender gender;
    private EActiveStatus status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifyDate;
    private String createdBy;
    private String modifyBy;
    private Stream<ERoleName> roles;
}
