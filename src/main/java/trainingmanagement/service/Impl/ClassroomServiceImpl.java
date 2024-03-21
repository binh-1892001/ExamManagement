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

package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.request.admin.AClassRequest;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.model.enums.EClassStatus;
import trainingmanagement.model.entity.Role;
import trainingmanagement.model.entity.User;
import trainingmanagement.repository.ClassroomRepository;
import trainingmanagement.repository.UserRepository;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.response.teacher.TClassResponse;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.service.ClassroomService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    /**
     * * Hàm dùng để lấy ra 1 List đối tượng Classroom trong Db.
     * @Param: None.
     * @Return: List<Classroom>.
     * */
    @Override
    public List<Classroom> getAllToList() {
        return classroomRepository.findAll();
    }
    /**
     * * Hàm dùng để lấy ra 1 Page chứa đối tượng Classroom trong Db.
     * * - Mặc dù truyền nhiều tham số nhưng việc phân trang sẽ được JPA đưa cho Db xử lý,
     * * nên BE sẽ không bị nặng vấn đề xử lý dữ liệu, tránh việc dữ liệu bị quá nhiều, nặng cho BE.
     * @Param: Integer limit: giới hạn 1 Page có bao nhiêu bản ghi
     * @Param: Integer page: phân trang hiện tại, trang bắt đầu từ số 0.
     * @Param: String sort: tên của trường (dựa theo Class) dùng để sắp xếp dựa theo trường đó.
     * @Param: String order: "asc/desc" cho phép sắp xếp xuôi hoặc ngược.
     * @Return: Page<Classroom>.
     * */
    @Override
    public Page<Classroom> getAllClassToPages(Integer limit, Integer page, String sort, String order)
            throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        Page<Classroom> classes = classroomRepository.findAll(pageable);
        if(classes.getContent().isEmpty()) throw new CustomException("Classes page is empty.");
        return classes;
    }
    /**
     * * Hàm dùng để tìm kiếm và lấy ra 1 Page chứa đối tượng Classroom trong Db.
     * * - Mặc dù truyền nhiều tham số nhưng việc phân trang sẽ được JPA đưa cho Db xử lý,
     * * nên BE sẽ không bị nặng vấn đề xử lý dữ liệu, tránh việc dữ liệu bị quá nhiều, nặng cho BE.
     * @Param: keyword: từ khoá dùng để tìm kiếm theo, cụ thể theo className của Class.
     * @Param: Integer limit: giới hạn 1 Page có bao nhiêu bản ghi
     * @Param: Integer page: phân trang hiện tại, trang bắt đầu từ số 0.
     * @Param: String sort: tên của trường (dựa theo Class) dùng để sắp xếp dựa theo trường đó.
     * @Param: String order: "asc/desc" cho phép sắp xếp xuôi hoặc ngược.
     * @Return: Page<Classroom>.
     * */
    @Override
    public Page<Classroom> searchAllClassByClassNameToPages(String className,
            Integer limit, Integer page, String sort, String order) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        Page<Classroom> classes = classroomRepository.searchByClassNameContainingIgnoreCase(pageable, className);
        if(classes.getContent().isEmpty()) throw new CustomException("Classes page is empty.");
        return classes;
    }
    /**
     * * Hàm dùng để chuyển đổi 1 Page từ Classroom sang AClassResponse.
     * * - AClassResponse: là dto Class dùng cho Admin Role.
     * @Param: Page<Classroom> nhận vào 1 Page kiểu Classroom.
     * @Return: Page<AClassResponse>.
     * */
    @Override
    public Page<AClassResponse> convertPagesToAClassResponse(Page<Classroom> classPage){
        List<AClassResponse> classes = classPage.stream().map(this::entityAMap).toList();
        return new PageImpl<>(classes, classPage.getPageable(), classPage.getTotalPages());
    }
    @Override
    public Optional<Classroom> getClassById(Long classId) {
        return classroomRepository.findById(classId);
    }
    @Override
    public AClassResponse getAClassResponseById(Long classId) throws CustomException{
        Optional<Classroom> optionalClass = getClassById(classId);
        // ? Exception cần tìm thấy thì mới có thể chuyển thành Dto.
        if(optionalClass.isEmpty()) throw new CustomException("Class is not exists.");
        Classroom classroom = optionalClass.get();
        return entityAMap(classroom);
    }
    @Override
    public Classroom save(Classroom classroom) {
        return classroomRepository.save(classroom);
    }
    @Override
    public Classroom save(AClassRequest classRequest) {
        return classroomRepository.save(entityAMap(classRequest));
    }
    public User userRoleTeacher(AClassRequest classRequest){
        Optional<User> userOptional = userRepository.findById(classRequest.getTeacherId());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            for(Role role: user.getRoles()){
                if (role.getRoleName().equals(ERoleName.ROLE_TEACHER)){
                    return user;
                }
            }
        }
        return null;
    }
    @Override
    public Classroom patchUpdate(Long classroomId, AClassRequest classRequest) {
        Optional<Classroom> updateClassroom = getClassById(classroomId);
        if (updateClassroom.isPresent()) {
            Classroom classroom = updateClassroom.get();
            if (classRequest.getClassName() != null)
                classroom.setClassName(classRequest.getClassName());
            if (classRequest.getClassStatus() != null) {
                if (classRequest.getClassStatus().equalsIgnoreCase(EClassStatus.NEW.name()))
                    classroom.setClassStatus(EClassStatus.NEW);
                else if (classRequest.getClassStatus().equalsIgnoreCase(EClassStatus.OJT.name()))
                    classroom.setClassStatus(EClassStatus.OJT);
                else classroom.setClassStatus(EClassStatus.FINISH);
            }
            if (classRequest.getTeacherId() != null) {
                if (userRoleTeacher(classRequest) != null) {
                    classroom.setTeacher(userRoleTeacher(classRequest));
                }
            }
            return save(classroom);
        }
        return null;
    }
    @Override
    public Classroom putUpdate(Long classId, AClassRequest classRequest) {
        return null;
    }
    @Override
    public void softDeleteByClassId(Long classId) throws CustomException{
        // ? Exception cần tìm thấy thì mới có thể xoá mềm.
        Optional<Classroom> deleteClass = getClassById(classId);
        if(deleteClass.isEmpty())
            throw new CustomException("Class is not exists to delete.");
        Classroom classroom = deleteClass.get();
        classroom.setStatus(EActiveStatus.INACTIVE);
        classroomRepository.save(classroom);
    }

    @Override
    public void hardDeleteByClassId(Long classId) throws CustomException {
        // ? Exception cần tìm thấy thì mới có thể xoá cứng.
        if(!classroomRepository.existsById(classId))
            throw new CustomException("Class is not exists to delete.");
        classroomRepository.deleteById(classId);
    }
    @Override
    public List<TClassResponse> teacherGetListClassrooms() {
        return classroomRepository.getAllByStatus(EActiveStatus.ACTIVE).stream().map(this::entityTMap).toList();
    }
    @Override
    public Optional<TClassResponse> teacherGetClassById(Long classroomId) {
        return getClassById(classroomId).map(this::entityTMap);
    }
    @Override
    public List<TClassResponse> teacherFindClassByName(String className) {
        return classroomRepository.findByClassNameContainingIgnoreCase(className)
                .stream().map(this::entityTMap).toList();
    }
    @Override
    public Classroom entityAMap(AClassRequest classRequest) {
        EClassStatus classStatus = switch (classRequest.getClassStatus()) {
            case "NEW" -> EClassStatus.NEW;
            case "OJT" -> EClassStatus.OJT;
            case "FINISH" -> EClassStatus.FINISH;
            default -> null;
        };
        if (userRoleTeacher(classRequest)!=null){
            return Classroom.builder()
                    .teacher(userRoleTeacher(classRequest))
                    .className(classRequest.getClassName())
                    .classStatus(classStatus)
                    .build();
        }
        return null;
    }
    @Override
    public AClassResponse entityAMap(Classroom classroom) {
        return AClassResponse.builder()
                .classId(classroom.getId())
                .className(classroom.getClassName())
                .classStatus(classroom.getClassStatus())
                .status(classroom.getStatus())
                .createdDate(classroom.getCreatedDate())
                .modifyDate(classroom.getModifyDate())
                .build();
    }
    @Override
    public TClassResponse entityTMap(Classroom classroom) {
        return TClassResponse.builder()
                .className(classroom.getClassName())
                .classStatus(classroom.getClassStatus())
                .build();
    }
}