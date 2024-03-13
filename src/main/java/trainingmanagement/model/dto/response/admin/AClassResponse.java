package trainingmanagement.model.dto.response.admin;

import lombok.*;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.EStatusClass;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AClassResponse {
    private String className;
    private EStatusClass classStatus;
    private EActiveStatus status;
}
