package trainingmanagement.model.dto.admin.request;

import lombok.*;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.ETypeTest;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TestRequest {
    private String nameTest;
    private String eActiveStatus;
    private Integer testTime;
    private ETypeTest typeTest;
    private String resources;
    private Long examId;
}
