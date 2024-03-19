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

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ATestRequest {
    private String testName;
    private Integer testTime;
    private String testType;
    private String resources;
    private String status;
    private Long examId;
}
