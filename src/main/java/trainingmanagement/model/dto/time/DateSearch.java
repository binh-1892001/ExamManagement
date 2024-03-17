package trainingmanagement.model.dto.time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DateSearch {
    private String createDate;
    private String startDate;
    private String endDate;
}
