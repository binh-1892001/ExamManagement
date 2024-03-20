/**
 * * Created by Nguyễn Đức Hải.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto request dùng cho admin.
 * * Dùng để Create/Update cho Class Entity.
 * @param className: tên của class.
 * @param classStatus: tiến độ của class (NEW, OJT, FINISH).
 * @param status: dùng để xoá mềm cho Entity.
 * @author: Nguyễn Đức Hải.
 * @since: 18/3/2024.
 * */

package trainingmanagement.model.dto.request.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AClassRequest {
    @NotEmpty(message = "Class name must not be Null.")
    private String className;
    @Pattern(regexp = "^(?i)(NEW|OJT/FINISH)$", message = "String value must be \"NEW/OJT/FINISH\"")
    private String classStatus;
    @NotNull(message = "Không được bỏ trống chỗ này nha!!")
    private Long teacherId;
    @Pattern(regexp = "^(?i)(ACTIVE|INACTIVE)$", message = "Chuỗi phải là 'ACTIVE' hoặc 'INACTIVE'")
    private String status;
}