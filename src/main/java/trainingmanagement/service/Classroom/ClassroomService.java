/**
 * * Fixed by NguyenHongQuan:
 * * - Create standard CRUD for ClassService.
 * * - Add both Put and Patch method to edit Class entity.
 * * - Add both softDelete and hardDelete to delete Class entity.
 * @author: NguyenMinhHoang.
 * @since: 13/3/2024.
 * */

package trainingmanagement.service.Classroom;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.ClassRequest;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.entity.Classroom;
import java.util.List;
import java.util.Optional;

public interface ClassroomService {
    List<Classroom> getAllToList();
    List<AClassResponse> getAllClassResponsesToList();
    Optional<Classroom> getClassById(Long classId);
    Optional<AClassResponse> getAClassResponseById(Long classId) throws CustomException;
    Classroom save(Classroom classroom);
    Classroom save(ClassRequest classRequest);
    Classroom putUpdate(Long classId, ClassRequest classRequest);
    Classroom patchUpdate(Long classId, ClassRequest classRequest);
    void softDeleteByClassId(Long classId) throws CustomException;
    void hardDeleteByClassId(Long classId) throws CustomException;
    List<AClassResponse> findByClassName(String className);
    AClassResponse entityMap(Classroom classroom);
    Classroom entityMap(ClassRequest classRequest);
}