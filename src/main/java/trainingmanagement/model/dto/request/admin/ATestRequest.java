/**
 * * Created by PhamVanTung.
 * * Fixed by NguyenHongQuan.
 * * - Test Dto Request for admin (done).
 * @author: Phạm Văn Tùng.
 * @since: 14/3/2024.
 * */

package trainingmanagement.model.dto.request.admin;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ATestRequest {
    private String testName;
    private Integer testTime;
    private String testType;
    private String resources;
    private Long examId;
    private String status;
}
