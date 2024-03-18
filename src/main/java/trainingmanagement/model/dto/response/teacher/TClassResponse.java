package trainingmanagement.model.dto.response.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.enums.EClassStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TClassResponse {
    private String className;
    private EClassStatus classStatus;
}
