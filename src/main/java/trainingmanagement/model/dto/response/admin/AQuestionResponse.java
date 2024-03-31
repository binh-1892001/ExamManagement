/**
 * * Created by Nguyễn Hồng Quân.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto response dùng cho admin.
 * * Dùng để ReadData cho Question Entity.
 * @param questionId: Id của Question.
 * @param questionContent: nội dung của Question (phần text câu hỏi của question).
 * @param questionLevel: độ khó của câu hỏi.
 * @param questionType: loại câu hỏi (câu hỏi 1 lựa chọn hay nhiều lựa chọn).
 * @param image: ảnh dùng cho câu hỏi (nếu có).
 * @param status: dùng để xoá mềm cho Entity.
 * @param test: thể hiện câu hỏi này của đề thi nào.
 * @param List<Option>: thông tin các phương án dành cho Question này.
 * @param createdDate: ngày tạo bản ghi.
 * @param modifyDate: ngày sửa đổi bản ghi gần nhất.
 * @param createdBy: thông tin user tạo bản ghi.
 * @param modifyBy: thông tin user sửa đổi bản ghi gần nhất.
 * @author: Nguyễn Hồng Quân.
 * @since: 18/3/2024.
 * */

package trainingmanagement.model.dto.response.admin;

import lombok.*;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EQuestionLevel;
import trainingmanagement.model.enums.EQuestionType;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AQuestionResponse {
    private Long questionId;
    private String questionContent;
    private EQuestionType questionType;
    private EQuestionLevel questionLevel;
    private String image;
    private EActiveStatus status;
    private String testName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifyDate;
    private String createdBy;
    private String modifyBy;
    private List<AOptionResponse> options;

}
