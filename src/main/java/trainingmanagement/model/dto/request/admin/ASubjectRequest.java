/**
 * * Created by Nguyễn Đức Hải.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto request dùng cho admin.
 * * Dùng để Create/Update cho Subject.
 * @param subjectName: tên của môn học (Subject).
 * @param status: dùng để xoá mềm cho Entity.
 * @author: Nguyễn Đức Hải.
 * @since: 18/3/2024.
 * */

package trainingmanagement.model.dto.request.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ASubjectRequest {
    @NotEmpty(message = "Not empty!!")
    private String subjectName;
    @Pattern(regexp = "^(?i)(ACTIVE|INACTIVE)$", message = "String must be 'ACTIVE' or 'INACTIVE'")
    private String status;
}