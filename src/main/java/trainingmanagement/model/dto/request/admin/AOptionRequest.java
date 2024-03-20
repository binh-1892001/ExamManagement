/**
 * * Created by Nguyễn Đức Hải.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto request dùng cho admin.
 * * Dùng để Create/Update cho Option.
 * @param optionContent: nội dung của Option (phần text phương án của Question).
 * @param isCorrect: xác định có phải đáp án đùng của question hay không.
 * @param status: dùng để xoá mềm cho Entity.
 * @param questionId: thể hiện tham chiếu, liên kết với Question Entity.
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
public class AOptionRequest {
    @NotEmpty(message = "Không được bỏ trống chỗ này nha!!")
    private String optionContent;
    @Pattern(regexp = "^(?i)(CORRECT|INCORRECT)$", message = "Chuỗi phải là 'CORRECT' hoặc 'INCORRECT'")
    private String isCorrect;
    @Pattern(regexp = "^(?i)(ACTIVE|INACTIVE)$", message = "Chuỗi phải là 'ACTIVE' hoặc 'INACTIVE'")
    private String status;
    @NotNull(message = "Không được bỏ trống chỗ này nha!!")
    private Long questionId;
}
