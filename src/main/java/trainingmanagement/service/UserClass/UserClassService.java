package trainingmanagement.service.UserClass;

import trainingmanagement.model.dto.admin.request.UserClassRequest;
import trainingmanagement.model.entity.UserClass;

import java.util.List;

public interface UserClassService {
    UserClass saveStudent(UserClassRequest userClassRequest);
    List<UserClass> findByClassId(Long classId);
}
