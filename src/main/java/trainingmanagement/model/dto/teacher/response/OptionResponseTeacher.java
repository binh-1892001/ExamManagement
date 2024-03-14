package trainingmanagement.model.dto.teacher.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OptionResponseTeacher {
    private Long optionId;
    private String contentOptions;
    private String status;
}
