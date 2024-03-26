package trainingmanagement.model.dto.time;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DateSearch {
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "không đúng định dạng! Ghi là 'yyyy-mm-dd'")
    private String createDate;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "không đúng định dạng! Ghi là 'yyyy-mm-dd'")
    private String startDate;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "không đúng định dạng! Ghi là 'yyyy-mm-dd'")
    private String endDate;
}
