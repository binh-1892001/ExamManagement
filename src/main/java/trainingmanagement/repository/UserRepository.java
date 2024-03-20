package trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.model.entity.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    @Query("select u from User u where u.id = :userId")
    Optional<User> findByUserId(Long userId);
    Page<User> findAll(Pageable pageable);
    boolean existsByUsername(String username);
    @Query(value = "SELECT * FROM user u where u.username LIKE CONCAT('%', :keyword, '%') OR u.full_name LIKE CONCAT('%', :keyword, '%')"
            , nativeQuery = true)
    List<User> findByUsernameOrFullNameContainingIgnoreCase(String keyword);

    //* Admin xem tat ca giao vien
    @Query(value = "select u.* from user u join user_role ur on u.id=ur.user_id " +
            "join role r on ur.role_id=r.id where role_name='ROLE_TEACHER'",nativeQuery = true)
    List<User> getAllTeacher();

//    //* Xem tat ca nguoi dung theo role va classId
//    @Query(value = "select u.* from role r join user_role ur on r.id=ur.role_id " +
//            "join user u on ur.user_id = u.id " +
//            "join user_class uc on u.id = uc.user_id " +
//            "where r.role_name = :roleName and uc.class_id = :classId", nativeQuery = true)
//    List<User> getAllByClassIdAndRole(ERoleName roleName, Long classId);

}