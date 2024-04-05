/**
 * * - Create standard CRUD for ClassService.
 * * - Add both Put and Patch method to edit Class entity.
 * * - Add both softDelete and hardDelete to delete Class entity.
 * * - Sửa lại để có thể lấy cả theo List, Page và ép kiểu theo từng role.
 * * - Sắp xếp lại các Method và Comment để có thể dễ đọc hơn.
 * @ModifyBy: Nguyễn Hồng Quân.
 * @ModifyDate: 20/03/2025.
 * @CreatedBy: Mguyễn Minh Hoàng.
 * @CreatedDate: 13/3/2024.
 * */

package trainingmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.model.dto.request.admin.AClassRequest;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.dto.response.teacher.TClassResponse;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface ClassroomService {
    // ? Lấy theo List và chuyển đổi bằng entityMap.
    List<Classroom> getAllToList();
    // ? Lấy theo Pages và chuyển đổi bằng entityMap.
    Page<Classroom> getAllClassToPages(
        Integer limit,
        Integer page,
        String sort,
        String order
    ) throws CustomException;
    Page<Classroom> searchAllClassByClassNameToPages(String className,
         Integer limit, Integer page, String sort, String order)
            throws CustomException;
    // * Func convert sang ứng với quyền admin.
    Page<AClassResponse> entityAMap(Page<Classroom> classPage);
    // ? Lấy theo Id và chuyển đổi bằng entityMap.
    Optional<Classroom> getClassById(Long classId);
    AClassResponse getAClassById(Long classId) throws CustomException;
    // ? CRUD Functions.
    Classroom save(Classroom classroom);
    AClassResponse createClass(AClassRequest classRequest) throws CustomException;
    boolean isTeacher(Long userId) throws CustomException;
    User getUserIfIsTeacher(Long userId) throws CustomException;
    AClassResponse putUpdateClass(Long classId, AClassRequest classRequest) throws CustomException;
    AClassResponse patchUpdateClass(Long classId, AClassRequest classRequest) throws CustomException;
    void softDeleteByClassId(Long classId) throws CustomException;
    void hardDeleteByClassId(Long classId) throws CustomException;
    // ? Functional dành cho Teacher.
    Page<TClassResponse> getTAllToList(Pageable pageable);
    Optional<TClassResponse> getTClassById(Long classId);
    Page<TClassResponse> findTClassByClassName(String className, Pageable pageable);
    // ? EntityMap dùng để ép kiểu dành cho Admin.
    AClassResponse entityAMap(Classroom classroom);
    Classroom entityAMap(AClassRequest classRequest) throws CustomException;
    // ? EntityMap dùng để ép kiểu dành cho Teacher.
    TClassResponse entityTMap(Classroom classroom);
    List<Classroom> getAllByTeacher(User teacher);
}