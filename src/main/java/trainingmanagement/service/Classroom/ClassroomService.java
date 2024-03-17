package trainingmanagement.service.Classroom;

import trainingmanagement.model.dto.request.admin.AClassRequest;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.entity.Classroom;
import java.util.List;
import java.util.Optional;

public interface ClassroomService {
    List<Classroom> getAllToList();
    List<AClassResponse> getAllClassResponsesToList();
    Optional<Classroom> getById(Long classroomId);
    Classroom save(Classroom classroom);
    Classroom save(AClassRequest AClassRequest);
    Classroom patchUpdate(Long classroomId, AClassRequest AClassRequest);
    void deleteById(Long classId);
    List<AClassResponse> findByClassName(String className);
    AClassResponse entityMap(Classroom classroom);
    Classroom entityMap(AClassRequest AClassRequest);
    Optional<Classroom> findByUserId(Long userId);
}