/**
 * * Created by Nguyễn Hồng Quân.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto response dùng cho admin.
 * * Dùng để ReadData cho Role Entity.
 * @param roleId: Id của Role.
 * @param roleName: tên của Role.
 * @author: Nguyễn Hồng Quân.
 * @since: 18/3/2024.
 * */

package trainingmanagement.model.dto.response.admin;

import lombok.*;
import trainingmanagement.model.enums.ERoleName;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ARoleResponse {
    private Long roleId;
    private ERoleName roleName;
}