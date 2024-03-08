package trainingmanagement.model.dto.responseEntity;

import lombok.*;
import trainingmanagement.model.entity.Enum.EStatusCode;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Response<T>{
    private EStatusCode status;
    private String HttpCodeName;
    private Number HttpCodeNumber;
    private T content;
}
