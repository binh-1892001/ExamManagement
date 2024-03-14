package trainingmanagement.service.Classroom;

import trainingmanagement.model.dto.admin.request.ClassRequest;
import trainingmanagement.model.dto.admin.response.ClassResponse;
import trainingmanagement.model.entity.Classroom;
import java.util.List;
import java.util.Optional;

public interface ClassroomService {
    List<Classroom> getAllToList();
    List<ClassResponse> getAllClassResponsesToList();
    Optional<Classroom> getById(Long classroomId);
    Classroom save(Classroom classroom);
    Classroom save(ClassRequest classRequest);
    Classroom patchUpdate(Long classroomId, ClassRequest classRequest);
    void deleteById(Long classId);
    List<ClassResponse> findByClassName(String className);
    ClassResponse entityMap(Classroom classroom);
    Classroom entityMap(ClassRequest classRequest);
    Optional<Classroom> findByUserId(Long userId);
}