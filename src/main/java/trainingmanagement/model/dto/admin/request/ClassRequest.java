package trainingmanagement.model.dto.admin.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClassRequest {
    private String className;
    private String status;
}