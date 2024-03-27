package trainingmanagement.model.dto.response.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.entity.User;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TResultResponse {
    private User student;
    private Test test;
    private LocalDate dateDoTest;
    private Double mark;
}
