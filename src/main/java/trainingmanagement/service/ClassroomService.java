/**
 * * Fixed by NguyenHongQuan:
 * * - Create standard CRUD for ClassService.
 * * - Add both Put and Patch method to edit Class entity.
 * * - Add both softDelete and hardDelete to delete Class entity.
 * @author: NguyenMinhHoang.
 * @since: 13/3/2024.
 * */

package trainingmanagement.service;

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
    Optional<Classroom> getClassById(Long classId);
    Classroom save(Classroom classroom);
    Classroom save(AClassRequest classRequest);
    Classroom putUpdate(Long classId, AClassRequest classRequest);
    Classroom patchUpdate(Long classroomId, AClassRequest classRequest);
    List<AClassResponse> findByClassName(String className);
    Optional<Classroom> findByUserId(Long userId);
    List<TClassResponse>teacherGetListClassrooms();
    AClassResponse getAClassResponseById(Long classId) throws CustomException;
    Optional<TClassResponse> teacherGetClassById(Long classroomId);
    List<TClassResponse> teacherFindClassByName(String className);
    void softDeleteByClassId(Long classId) throws CustomException;
    void hardDeleteByClassId(Long classId) throws CustomException;
    AClassResponse entityAMap(Classroom classroom);
    Classroom entityAMap(AClassRequest classRequest);
    TClassResponse entityTMap(Classroom classroom);
}