/**
 * * Created by NguyenHongQuan.
 * * - Test Dto Response for admin (done).
 * @author: Nguyễn Hồng Quân.
 * @since: 14/3/2024.
 * */

package trainingmanagement.model.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.ETestType;

import java.time.LocalDate;

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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifyDate;
    private String createdBy;
    private String modifyBy;
    private EActiveStatus status;
}
