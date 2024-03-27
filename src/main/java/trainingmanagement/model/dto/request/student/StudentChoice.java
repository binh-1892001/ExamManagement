package trainingmanagement.model.dto.request.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StudentChoice {
    private Long idQuestion;
    private List<Long> listIdOptionStudentChoice;
}
