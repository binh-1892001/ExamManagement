package trainingmanagement.model.dto;

import lombok.*;
import trainingmanagement.model.entity.Enum.EStatusClass;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClassroomRequest {
    private String nameClass;
    private String status;
}
