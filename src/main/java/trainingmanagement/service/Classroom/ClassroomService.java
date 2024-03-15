/**
 * * Fixed by NguyenHongQuan:
 * * - Create standard CRUD for ClassService.
 * * - Add both Put and Patch method to edit Class entity.
 * * - Add both softDelete and hardDelete to delete Class entity.
 * @author: NguyenMinhHoang.
 * @since: 13/3/2024.
 * */

package trainingmanagement.service.Classroom;

import trainingmanagement.model.dto.request.admin.AClassRequest;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.dto.response.teacher.TClassResponse;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.entity.Classroom;
import java.util.List;
import java.util.Optional;

public interface ClassroomService {
    List<Classroom> getAllToList();
    List<AClassResponse> getAllClassResponsesToList();
    List<TClassResponse>teacherGetListClassrooms();
    Optional<Classroom> getClassById(Long classId);
    AClassResponse getAClassResponseById(Long classId) throws CustomException;
    Optional<TClassResponse> teacherGetClassById(Long classroomId);
    List<AClassResponse> findByClassName(String className);
    List<TClassResponse> teacherFindClassByName(String className);
    Classroom save(Classroom classroom);
    Classroom save(AClassRequest AClassRequest);
    Classroom putUpdate(Long classId, AClassRequest AClassRequest);
    Classroom patchUpdate(Long classId, AClassRequest AClassRequest);
    void softDeleteByClassId(Long classId) throws CustomException;
    void hardDeleteByClassId(Long classId) throws CustomException;
    AClassResponse entityAMap(Classroom classroom);
    Classroom entityAMap(AClassRequest AClassRequest);
    TClassResponse entityTMap(Classroom classroom);
}