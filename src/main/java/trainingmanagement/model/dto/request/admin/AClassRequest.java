/**
 * * Created by Nguyễn Đức Hải.
 * * Fixed by Nguyễn Hồng Quân.
 * ? Thêm trường status để xoá mềm.
 * ? Thêm comment cho file code.
 * * Dto request dùng cho admin.
 * * Dùng để Create/Update cho Class Entity.
 * @param className: tên của class.
 * @param classStatus: tiến độ của class (NEW, OJT, FINISH).
 * @param status: dùng để xoá mềm cho Entity.
 * @author: Nguyễn Đức Hải.
 * @since: 18/3/2024.
 * */

package trainingmanagement.model.dto.request.admin;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AClassRequest {
    private String className;
    private String classStatus;
    private Long teacherId;
    private String status;
}