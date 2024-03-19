/**
 * * Created by Nguyễn Minh Hoàng.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm comment cho file code.
 * * Dto request dùng cho teacher.
 * * Dùng để Create/Update cho Class Entity.
 * @param className: tên của class.
 * @author: Nguyễn Minh Hoàng.
 * @since: 18/3/2024.
 * */

package trainingmanagement.model.dto.request.teacher;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TClassRequest {
    @NotEmpty(message = "Không được bỏ trống chỗ này nha!!")
    private String className;
}
