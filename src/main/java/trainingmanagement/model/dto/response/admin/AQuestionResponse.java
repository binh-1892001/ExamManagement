package trainingmanagement.model.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EQuestionLevel;
import trainingmanagement.model.enums.EQuestionType;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AQuestionResponse {
    private Long questionId;
    private String contentQuestion;
    private EQuestionType typeQuestion;
    private EQuestionLevel levelQuestion;
    private String image;
    private EActiveStatus status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    private String testName;
    private List<AOptionResponse> options;
}
