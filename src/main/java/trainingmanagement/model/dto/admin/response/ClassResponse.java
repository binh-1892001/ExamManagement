package trainingmanagement.model.dto.admin.response;

import lombok.*;
import trainingmanagement.model.entity.Enum.EStatusClass;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClassResponse {
    private String className;
    private EStatusClass status;
}