package trainingmanagement.model.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.model.enums.EActiveStatus;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AExamResponse {
    private Long examId;
    private String examName;
    private EActiveStatus status;
    private Subject subject;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifyDate;
}
