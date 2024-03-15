package trainingmanagement.model.dto.response.admin;

import lombok.*;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.EClassStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AClassResponse {
    private String className;
    private EClassStatus classStatus;
    private EActiveStatus status;
}
