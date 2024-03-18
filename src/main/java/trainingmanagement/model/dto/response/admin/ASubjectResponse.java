/**
 * * Created by Nguyễn Hồng Quân.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto response dùng cho admin.
 * * Dùng để ReadData cho Subject Entity.
 * @param subjectId: Id của Subject.
 * @param subjectName: tên của môn học.
 * @param status: dùng để xoá mềm cho Entity.
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
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ASubjectResponse {
    private Long subjectId;
    private String subjectName;
    private EActiveStatus status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifyDate;
    private String createdBy;
    private String modifyBy;
}
