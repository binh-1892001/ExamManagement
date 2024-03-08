package trainingmanagement.model.dto.responseEntity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import trainingmanagement.model.entity.Enum.ELevelQuestion;
import trainingmanagement.model.entity.Enum.ETypeQuestion;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseQuestionQuiz {
    private String contentQuestion;
    private Boolean status;
    private ETypeQuestion typeQuestion;
    private ELevelQuestion levelQuestion;
}
