package trainingmanagement.model.dto.response.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EOptionStatus;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TOptionResponse {
    private Long optionId;
    private String contentOptions;
    private EActiveStatus status;
    private EOptionStatus isCorrect;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate modifyDate;
    private String createdBy;
    private String modifyBy;
}
