/**
 * * Created by Nguyễn Đức Hải.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto request dùng cho admin.
 * * Dùng để Create/Update cho Test.
 * @param testName: tên của đề thi (Test).
 * @param testTime: thời gian làm đề thi (giờ).
 * @param testType: kiểu đề thi (QUIZTEST, WRITENTEST: trắc nghiệm, thực hành).
 * @param resources: tài nguyên (dùng cho đề thi thực hành).
 * @param status: dùng để xoá mềm cho Entity.
 * @param examId: thể hiện tham chiếu, liên kết với exam Entity.
 * @author: Nguyễn Đức Hải.
 * @since: 18/3/2024.
 * */

package trainingmanagement.model.dto.request.admin;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ATestRequest {
    @NotEmpty(message = "Not empty!!")
    private String testName;
    @Min(15)
    @Max(150)
    private Integer testTime;
    @Pattern(regexp = "^(?i)(WRITENTEST|QUIZTEST)$", message = "String must be 'WRITENTEST' or 'QUIZTEST'")
    private String testType;
    private String resources;
    @Pattern(regexp = "^(?i)(ACTIVE|INACTIVE)$", message = "String must be 'ACTIVE' or 'INACTIVE'")
    private String status;
    @NotNull(message = "Not null!!")
    private Long examId;
}
