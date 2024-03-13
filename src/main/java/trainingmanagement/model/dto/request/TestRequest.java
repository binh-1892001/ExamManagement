package trainingmanagement.model.dto.request;

import lombok.*;
import trainingmanagement.model.entity.Enum.ETypeTest;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TestRequest {
    private String nameTest;
    private Boolean status;
    private int time;
    private ETypeTest typeTest;
    private String resources;
    private Long examId;
}
