/**
 * * Created by Nguyễn Hồng Quân.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto response dùng cho admin.
 * * Dùng để ReadData cho Exam Entity.
 * @param examId: Id của Exam.
 * @param examName: tên của Exam.
 * @param status: dùng để xoá mềm cho Entity.
 * @param subject: thể hiện bài thi này của môn học nào.
 * @param List<Subject>: thông tin các môn học dành cho Class (cần thêm vào sau này).
 * @param createdDate: ngày tạo bản ghi.
 * @param modifyDate: ngày sửa đổi bản ghi gần nhất.
 * @param createdBy: thông tin user tạo bản ghi.
 * @param modifyBy: thông tin user sửa đổi bản ghi gần nhất.
 * @author: Nguyễn Hồng Quân.
 * @since: 18/3/2024.
 * */

package trainingmanagement.model.dto.response.admin;

import lombok.*;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.entity.Subject;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AExamResponse {
    private Long examId;
    private String examName;
    private EActiveStatus status;
    private String subjectName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifyDate;
    private String createdBy;
    private String modifyBy;
}
