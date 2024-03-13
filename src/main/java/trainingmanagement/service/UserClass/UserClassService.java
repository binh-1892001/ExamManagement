package trainingmanagement.service.UserClass;

import trainingmanagement.model.dto.request.UserClassRequest;
import trainingmanagement.model.entity.UserClass;

public interface UserClassService {
    UserClass saveStudent(UserClassRequest userClassRequest);
}
