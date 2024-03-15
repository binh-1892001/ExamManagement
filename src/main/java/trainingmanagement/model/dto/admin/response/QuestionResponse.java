package trainingmanagement.model.dto.admin.response;

import lombok.*;
import trainingmanagement.model.entity.Enum.EActiveStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionResponse {
    private Long questionId;
    private String contentQuestion;
    private String typeQuestion;
    private String levelQuestion;
    private String image;
    private String eActiveStatus;
    private String createdDate;
    private String testName;
    private List<OptionResponse> optionResponses;
}
