package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import trainingmanagement.service.ClassroomService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<Classroom> getAllToList() {
        return classroomRepository.findAll();
    }
    public List<AClassResponse> getAllClassResponsesToList(){
        return getAllToList().stream().map(this::entityMap).toList();
    }
    @Override
    public Optional<Classroom> getById(Long classroomId) {
        return classroomRepository.findById(classroomId);
    }
    @Override
    public Classroom save(Classroom classroom) {
        return classroomRepository.save(classroom);
    }
    @Override
    public Classroom save(AClassRequest AClassRequest) {
        return classroomRepository.save(entityMap(AClassRequest));
    }
    /**
     * @param classroomId
     * @param AClassRequest
     * @return
     * author:
     * */

    @Override
    public Classroom patchUpdate(Long classroomId, AClassRequest AClassRequest) {
        Optional<Classroom> updateClassroom = getById(classroomId);
        if(updateClassroom.isPresent()) {
            Classroom classroom = updateClassroom.get();
            if (AClassRequest.getClassName() != null)
                classroom.setClassName(AClassRequest.getClassName());
            if (AClassRequest.getClassStatus() != null) {
                if (AClassRequest.getClassStatus().equalsIgnoreCase(EClassStatus.NEW.name()))
                    classroom.setClassStatus(EClassStatus.NEW);
                else if (AClassRequest.getClassStatus().equalsIgnoreCase(EClassStatus.OJT.name()))
                    classroom.setClassStatus(EClassStatus.OJT);
                else classroom.setClassStatus(EClassStatus.FINISH);
            }
            if (AClassRequest.getTeacherId()!=null){
                if (userRoleTeacher(AClassRequest)!=null){
                    classroom.setTeacher(userRoleTeacher(AClassRequest));
                }
            }
            return save(classroom);
        }
        return null;
    }

    @Override
    public void deleteById(Long classId) {
        classroomRepository.deleteById(classId);
    }

    @Override
    public List<AClassResponse> findByClassName(String className) {
        return classroomRepository.findByClassNameContainingIgnoreCase(className)
                .stream().map(this::entityMap).toList();
    }
    @Override
    public Classroom entityMap(AClassRequest AClassRequest) {
        EClassStatus classStatus = switch (AClassRequest.getClassStatus()) {
            case "NEW" -> EClassStatus.NEW;
            case "OJT" -> EClassStatus.OJT;
            case "FINISH" -> EClassStatus.FINISH;
            default -> null;
        };
        if (userRoleTeacher(AClassRequest)!=null){
            return Classroom.builder()
                    .teacher(userRoleTeacher(AClassRequest))
                    .className(AClassRequest.getClassName())
                    .classStatus(classStatus)
                    .build();
        }
        return null;
    }

    @Override
    public Optional<Classroom> findByUserId(Long userId) {
        return classroomRepository.findByUserId(userId);
    }
    //* User khi check la teacher
    public User userRoleTeacher(AClassRequest AClassRequest){
        Optional<User> userOptional = userRepository.findById(AClassRequest.getTeacherId());
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
    public AClassResponse entityMap(Classroom classroom) {
        return AClassResponse.builder()
            .className(classroom.getClassName())
            .classStatus(classroom.getClassStatus())
            .status(classroom.getStatus())
            .build();
    }
}
