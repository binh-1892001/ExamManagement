package trainingmanagement.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SubjectRequest {
    private String nameSubject;
    private String timeToStudy;
    private Boolean status;
    private int time;
}
