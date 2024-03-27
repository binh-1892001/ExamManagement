package trainingmanagement.model.dto.request.admin;

import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AQuestionOptionRequest {
    AQuestionRequest questionRequest;
    List<AOptionRequest> optionRequests;
}
