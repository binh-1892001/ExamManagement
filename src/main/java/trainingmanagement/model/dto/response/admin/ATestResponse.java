/**
 * * Created by Nguyễn Hồng Quân.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto response dùng cho admin.
 * * Dùng để ReadData cho Test Entity.
 * @param testId: Id của Test.
 * @param testName: tên của đề thi (Test).
 * @param testTime: thời gian làm đề thi (giờ).
 * @param testType: kiểu đề thi (QUIZTEST, WRITENTEST: trắc nghiệm, thực hành).
 * @param resources: tài nguyên (dùng cho đề thi thực hành).
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
import trainingmanagement.model.enums.ETestType;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ATestResponse {
    private Long testId;
    private String testName;
    private Integer testTime;
    private ETestType testType;
    private String resources;
    private EActiveStatus status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifyDate;
    private String createdBy;
    private String modifyBy;
}