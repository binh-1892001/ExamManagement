package trainingmanagement.service.Classroom;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AClassRequest;
import trainingmanagement.model.dto.request.admin.ATestRequest;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.dto.response.teacher.TClassResponse;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.EClassStatus;
import trainingmanagement.repository.ClassroomRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService{
    private final ClassroomRepository classroomRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<Classroom> getAllToList() {
        return classroomRepository.findAll();
    }
    @Override
    public List<TClassResponse> teacherGetListClassrooms() {
        return classroomRepository.getAllByStatus(EActiveStatus.ACTIVE).stream().map(this::entityTMap).toList();
    }

    @Override
    public Optional<TClassResponse> teacherGetClassById(Long classroomId) {
        return getClassById(classroomId).map(this::entityTMap);
    }
    public List<AClassResponse> getAllClassResponsesToList(){
        return getAllToList().stream().map(this::entityAMap).toList();
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

    @Override
    public Classroom putUpdate(Long classId, AClassRequest classRequest) {
        return null;
    }
    @Override
    public Classroom patchUpdate(Long classroomId, AClassRequest classRequest) {
        Optional<Classroom> updateClassroom = getClassById(classroomId);
        if(updateClassroom.isPresent()) {
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
            return save(classroom);
        }
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
    public List<AClassResponse> findByClassName(String className) {
        return classroomRepository.findByClassNameContainingIgnoreCase(className)
                .stream().map(this::entityAMap).toList();
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
        return Classroom.builder()
            .className(classRequest.getClassName())
            .classStatus(classStatus)
            .build();
    }
    @Override
    public AClassResponse entityAMap(Classroom classroom) {
        return AClassResponse.builder()
            .className(classroom.getClassName())
            .classStatus(classroom.getClassStatus())
            .status(classroom.getStatus())
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
