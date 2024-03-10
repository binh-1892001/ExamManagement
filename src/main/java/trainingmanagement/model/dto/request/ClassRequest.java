package trainingmanagement.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClassRequest {
    private String className;
    private String status;
}