package trainingmanagement.service.UserClass;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.request.admin.AUserClassRequest;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Enum.ERoleName;
import trainingmanagement.model.entity.Role;
import trainingmanagement.model.entity.User;
import trainingmanagement.model.entity.UserClass;
import trainingmanagement.repository.ClassroomRepository;
import trainingmanagement.repository.UserClassRepository;
import trainingmanagement.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserClassServiceImp implements UserClassService{
    private final UserClassRepository userClassRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    @Override
    public UserClass saveStudent(AUserClassRequest AUserClassRequest) {
        Optional<User> userOptional = userRepository.findById(AUserClassRequest.getUserId());
        Optional<Classroom> classroomOptional = classroomRepository.findById(AUserClassRequest.getClassId());
        UserClass userClass = new UserClass();
        if (userOptional.isPresent() && classroomOptional.isPresent()){
            User user = userOptional.get();
            Classroom classroom = classroomOptional.get();
            for (Role role:user.getRoles()){
                if (role.getRoleName().equals(ERoleName.ROLE_STUDENT)){
                    if (userClassRepository.findByUserAndClassroom(user,classroom)==null){
                        userClass.setUser(user);
                        userClass.setClassroom(classroom);
                        return userClassRepository.save(userClass);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<UserClass> findByClassId(Long classId) {
        return userClassRepository.findStudentByClassId(classId);
    }
//
//    @Override
//    public UserClass saveTeacher(UserClassRequest userClassRequest) {
//        Optional<User> userOptional = userRepository.findById(userClassRequest.getUserId());
//        Optional<Classroom> classroomOptional = classroomRepository.findById(userClassRequest.getClassId());
//        if (userOptional.isPresent() && classroomOptional.isPresent()){
//            List<User> users = userRepository.getAllByClassIdAndRole(ERoles.ROLE_TEACHER, userClassRequest.getClassId());
//            for(User user:users){
//                for (Role role:user.getRoles()){
//                    if (!role.getRoleName().equals(ERoles.ROLE_TEACHER) && !role.getRoleName().equals(ERoles.ROLE_ADMIN)){
//                        UserClass userClass = new UserClass();
//                        userClass.setUser(userOptional.get());
//                        userClass.setClassroom(classroomOptional.get());
//                        return userClassRepository.save(userClass);
//                    }else if (role.getRoleName().equals(ERoles.ROLE_TEACHER)){
//                        UserClass userClass = userClassRepository.findByClassroom(classroomOptional.get());
//                        userClass.setUser(userOptional.get());
//                        return userClassRepository.save(userClass);
//                    }
//                }
//            }
//        }
//        return null;
//    }
}
