package trainingmanagement.model.dto.request.admin;

import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AQuestionOptionRequest {
    AQuestionRequest AQuestionRequest;
    List<AOptionRequest> AOptionRequests;
}
