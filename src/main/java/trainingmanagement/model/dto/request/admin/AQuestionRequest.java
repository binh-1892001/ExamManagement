/**
 * * Created by Nguyễn Đức Hải.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto request dùng cho admin.
 * * Dùng để Create/Update cho Question.
 * @param questionContent: nội dung của Question (phần text câu hỏi của question).
 * @param questionLevel: độ khó của câu hỏi.
 * @param questionType: loại câu hỏi (câu hỏi 1 lựa chọn hay nhiều lựa chọn).
 * @param image: ảnh dùng cho câu hỏi (nếu có).
 * @param status: dùng để xoá mềm cho Entity.
 * @param testId: thể hiện tham chiếu, liên kết với Test Entity.
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
public class AQuestionRequest {
    @NotEmpty(message = "Không được bỏ trống chỗ này nha!!")
    private String questionContent;
    @Pattern(regexp = "^(?i)(EASY|NORMAL|DIFFICULTY)$", message = "Chuỗi phải là 'EASY' hoặc 'NORMAL' hoặc 'DIFFICULTY' ")
    private String questionLevel;
    @Pattern(regexp = "^(?i)(MULTIPLE|SINGLE)$", message = "Chuỗi phải là 'MULTIPLE' hoặc 'SINGLE'")
    private String questionType;
    private String image;
    @Pattern(regexp = "^(?i)(ACTIVE|INACTIVE)$", message = "Chuỗi phải là 'ACTIVE' hoặc 'INACTIVE'")
    private String status;
    @NotNull(message = "Không được bỏ trống chỗ này nha!!")
    private Long testId;
}
