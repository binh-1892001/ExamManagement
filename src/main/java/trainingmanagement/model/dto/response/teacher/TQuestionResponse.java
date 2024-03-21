package trainingmanagement.model.dto.response.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import trainingmanagement.model.dto.response.admin.AOptionResponse;
import trainingmanagement.model.entity.Test;
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
public class TQuestionResponse {
    private Long questionId;
    private String contentQuestion;
    private EQuestionType typeQuestion;
    private EQuestionLevel levelQuestion;
    private String image;
    private EActiveStatus status;
    private Test test;
    private List<TOptionResponse> options;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifyDate;
    private String createdBy;
    private String modifyBy;
}
