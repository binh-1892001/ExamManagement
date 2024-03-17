package trainingmanagement.model.dto.request.admin;

import lombok.*;
import trainingmanagement.model.entity.Enum.ETestType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ATestRequest {
    private String nameTest;
    private String eActiveStatus;
    private Integer testTime;
    private ETestType typeTest;
    private String resources;
    private Long examId;
}
