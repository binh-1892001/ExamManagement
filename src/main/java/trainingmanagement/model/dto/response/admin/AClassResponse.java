/**
 * * Created by Nguyễn Hồng Quân.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto response dùng cho admin.
 * * Dùng để ReadData cho Class Entity.
 * @param classId: Id của Class.
 * @param className: tên của class.
 * @param classStatus: tiến độ của class (NEW, OJT, FINISH).
 * @param status: dùng để xoá mềm cho Entity.
 * @param teacherId (teacherFullName/teacherUsername): thông tin giáo viên cho Class (cần thêm vào sau này).
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
import trainingmanagement.model.enums.EClassStatus;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AClassResponse {
    private Long classId;
    private String className;
    private EClassStatus classStatus;
    private EActiveStatus status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifyDate;
    private String createdBy;
    private String modifyBy;
    private String teacherName;
}
