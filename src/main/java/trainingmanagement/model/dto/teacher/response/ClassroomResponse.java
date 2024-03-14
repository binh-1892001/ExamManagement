package trainingmanagement.model.dto.teacher.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.entity.Enum.EStatusClass;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClassroomResponse {
    private String className;
    private EStatusClass status;
}
