package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AUserClassRequest;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.model.entity.Role;
import trainingmanagement.model.entity.User;
import trainingmanagement.model.entity.UserClass;
import trainingmanagement.repository.ClassroomRepository;
import trainingmanagement.repository.UserClassRepository;
import trainingmanagement.repository.UserRepository;
import trainingmanagement.service.UserClassService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserClassServiceImp implements UserClassService {
    private final UserClassRepository userClassRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    @Override
    public UserClass add(AUserClassRequest aUserClassRequest) throws CustomException {
        Optional<User> userOptional = userRepository.findById(aUserClassRequest.getUserId());
        Optional<Classroom> classroomOptional = classroomRepository.findById(aUserClassRequest.getClassId());
        UserClass userClass = new UserClass();
        if (userOptional.isPresent() && classroomOptional.isPresent()){
            User user = userOptional.get();
            Classroom classroom = classroomOptional.get();
            for (Role role:user.getRoles()){
                if (role.getRoleName().equals(ERoleName.ROLE_STUDENT)){
                    if (userClassRepository.findByUserAndClassroom(user,classroom) == null){
                        userClass.setUser(user);
                        userClass.setClassroom(classroom);
                        return userClassRepository.save(userClass);
                    }
                    throw new CustomException("Student and class existed!");
                }
            }
        }
        throw new CustomException("Student or class are exist!");
    }

    @Override
    public UserClass update(AUserClassRequest userClassRequest,Long id) throws CustomException {
        Optional<UserClass> userClassOptional = findById(id);
        Optional<User> userOptional = userRepository.findById(userClassRequest.getUserId());
        Optional<Classroom> classroomOptional = classroomRepository.findById(userClassRequest.getClassId());
        if (userClassOptional.isPresent()){
            UserClass userClass = userClassOptional.get();
            if (userOptional.isPresent() && classroomOptional.isPresent()){
                User user = userOptional.get();
                Classroom classroom = classroomOptional.get();
                for (Role role:user.getRoles()){
                    if (role.getRoleName().equals(ERoleName.ROLE_STUDENT)){
                        if (userClassRepository.findByUserAndClassroom(user,classroom) == null){
                            userClass.setUser(user);
                            userClass.setClassroom(classroom);
                            return userClassRepository.save(userClass);
                        }
                        throw new CustomException("Student and class existed!");
                    }
                }
            }
            throw new CustomException("Student and class not exist!");
        }
        throw new CustomException("Student or class are exist!");
    }

    @Override
    public void deleteById(Long id) {
        userClassRepository.deleteById(id);
    }

    @Override
    public Optional<UserClass> findById(Long id) {
        return userClassRepository.findById(id);
    }

    @Override
    public List<UserClass> findByClassId(Long classId) {
        return userClassRepository.findStudentByClassId(classId);
    }

    @Override
    public List<UserClass> findClassByStudent(Long studentId) {
        return userClassRepository.findClassByStudent(studentId);
    }
}
