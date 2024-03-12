package trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Role;
import trainingmanagement.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Page<User> findAll(Pageable pageable);
    boolean existsByUsername(String username);
    @Query(value = "SELECT * FROM user u where u.username LIKE CONCAT('%', :keyword, '%') OR u.full_name LIKE CONCAT('%', :keyword, '%')"
            , nativeQuery = true)
    List<User> findByUsernameOrFullNameContainingIgnoreCase(String keyword);
    @Query(value = "select u.* from user u join user_class uc on u.id=uc.user_id " +
            "where class_id=(select class_id from user_class where user_id = :userId)",nativeQuery = true)
    List<User> getAllStudentInClassroom(Long userId);

    @Query(value = "select u.* from user u join user_role ur on u.id=ur.user_id " +
            "join role r on ur.role_id=r.id where role_name='ROLE_TEACHER'",nativeQuery = true)
    List<User> getAllTeacher();

    @Query(value = "select u.* from user_role ur join user u on ur.user_id = u.id " +
            "join user_class uc on u.id = uc.user_id where ur.role_id = 3 and uc.class_id = :classId", nativeQuery = true)
    List<User> getAllStudentByClassId(Long classId);
}