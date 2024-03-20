package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.User;
import trainingmanagement.model.entity.UserClass;
import java.util.List;

@Repository
public interface UserClassRepository extends JpaRepository<UserClass,Long> {

    UserClass findByUserAndClassroom(User user, Classroom classroom);

    @Query("select uc from UserClass uc where uc.classroom.id = :classId")
    List<UserClass> findStudentByClassId(Long classId);

    @Query("select uc from UserClass uc where uc.user.id = :studentId")
    List<UserClass> findClassByStudent(Long studentId);
}
