package trainingmanagement.service;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AUserClassRequest;
import trainingmanagement.model.entity.UserClass;

import java.util.List;
import java.util.Optional;

public interface UserClassService {
    UserClass add(AUserClassRequest AUserClassRequest) throws CustomException;
    UserClass update(AUserClassRequest userClassRequest,Long id) throws CustomException;
    void deleteById(Long id);
    Optional<UserClass> findById(Long id);
    List<UserClass> findByClassId(Long classId);
    List<UserClass> findClassByStudent(Long studentId);


}
