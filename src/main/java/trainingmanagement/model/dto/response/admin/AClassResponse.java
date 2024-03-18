package trainingmanagement.model.dto.response.admin;

import lombok.*;
import trainingmanagement.model.enums.EClassStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AClassResponse {
    private String className;
    private EClassStatus status;
}
