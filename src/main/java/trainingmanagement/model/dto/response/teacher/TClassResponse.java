package trainingmanagement.model.dto.response.teacher;

import lombok.*;
import trainingmanagement.model.enums.EClassStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TClassResponse {
    private String className;
    private EClassStatus classStatus;
}
