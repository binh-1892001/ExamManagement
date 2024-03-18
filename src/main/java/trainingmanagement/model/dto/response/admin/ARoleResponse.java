package trainingmanagement.model.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.enums.ERoleName;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ARoleResponse {
    private ERoleName roleName;
}