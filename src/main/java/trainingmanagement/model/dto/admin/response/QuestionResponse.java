package trainingmanagement.model.dto.admin.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import trainingmanagement.model.entity.Enum.EActiveStatus;

import java.time.LocalDate;
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    private String testName;
    private List<OptionResponse> optionResponses;
}
