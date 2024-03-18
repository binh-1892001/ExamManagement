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
