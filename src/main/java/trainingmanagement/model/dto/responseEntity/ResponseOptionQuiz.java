package trainingmanagement.model.dto.responseEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseOptionQuiz {
    private Long id;
    private String contentOptions;
    private Boolean status;
}
