package trainingmanagement.service;

import trainingmanagement.model.dto.request.admin.AUserClassRequest;
import trainingmanagement.model.entity.UserClass;

import java.util.List;

public interface UserClassService {
    UserClass saveStudent(AUserClassRequest AUserClassRequest);
    List<UserClass> findByClassId(Long classId);
}
