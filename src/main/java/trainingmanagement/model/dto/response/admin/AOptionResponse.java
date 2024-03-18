/**
 * * Created by Nguyễn Hồng Quân.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto response dùng cho admin.
 * * Dùng để ReadData cho Exam Entity.
 * @param optionId: Id của Option.
 * @param optionContent: nội dung của Option (phương án của Question).
 * @param isCorrect: xác định có phải đáp án đùng của question hay không.
 * @param status: dùng để xoá mềm cho Entity.
 * @param question: thể hiện phương án này của câu hỏi nào.
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
import trainingmanagement.model.enums.EOptionStatus;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AOptionResponse {
    private Long optionId;
    private String optionContent;
    private EOptionStatus isCorrect;
    private EActiveStatus status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifyDate;
    private String createdBy;
    private String modifyBy;
}