package trainingmanagement.model.dto.Wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainingmanagement.model.entity.Enum.EHttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseWrapper <T>{
    private EHttpStatus httpStatus;
    private Integer httpStatusCode;
    private String httpStatusName;
    private T content;
}
