package trainingmanagement.model.dto.response.teacher;

import lombok.*;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.entity.User;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TResultResponse {
    private User student;
    private Test test;
    private LocalDate dateDoTest;
    private Double mark;
}
