package trainingmanagement.model.dto.response.student;

import lombok.*;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.entity.User;

import java.time.LocalDate;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SResultResponse {
    private Long id;
    private String createdDate;
    private Long userId;
    private String userName;
    private Long testId;
    private String testName;
    private String status;
    private Double mark;
    private Integer examTimes;
}
