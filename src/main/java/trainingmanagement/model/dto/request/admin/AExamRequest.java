/**
 * * Created by Nguyễn Đức Hải.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto request dùng cho admin.
 * * Dùng để Create/Update cho Exam.
 * @param examName: tên của Exam.
 * @param status: dùng để xoá mềm cho Entity.
 * @param subjectId: thể hiện tham chiếu, liên kết với Subject Entity.
 * @author: Nguyễn Đức Hải.
 * @since: 18/3/2024.
 * */

package trainingmanagement.model.dto.request.admin;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AExamRequest {
    private String examName;
    private String status;
    private Long subjectId;
}