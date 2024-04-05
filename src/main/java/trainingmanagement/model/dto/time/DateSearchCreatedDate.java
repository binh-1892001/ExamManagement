package trainingmanagement.model.dto.time;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DateSearchCreatedDate {
    @NotNull(message = "Not null")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Not correct format date! Date must be 'yyyy-mm-dd'")
    private String createDate;
}
