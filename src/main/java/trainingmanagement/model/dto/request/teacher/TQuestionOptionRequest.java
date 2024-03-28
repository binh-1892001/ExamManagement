package trainingmanagement.model.dto.request.teacher;

import lombok.*;
import trainingmanagement.model.dto.request.admin.AOptionRequest;
import trainingmanagement.model.dto.request.admin.AQuestionRequest;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TQuestionOptionRequest {
    TQuestionRequest questionRequest;
    List<TOptionRequest> optionRequests;
}
