package trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByUsername(String username);
//    @Query(value = "SELECT * FROM user u where u.username LIKE CONCAT('%', :keyword, '%') OR u.full_name LIKE CONCAT('%', :keyword, '%')"
//            , nativeQuery = true)
    @Query("SELECT u from User u where u.username LIKE CONCAT('%', :keyword, '%') OR u.fullName LIKE CONCAT('%', :keyword, '%')")
    Page<User> findByUsernameOrFullNameContainingIgnoreCase(String keyword, Pageable pageable);
    //* Admin xem tat ca giao vien
//    @Query(value = "select u.* from user u join user_role ur on u.id=ur.user_id " +
//            "join role r on ur.role_id=r.id where role_name='ROLE_TEACHER'",nativeQuery = true)
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName = 'ROLE_TEACHER'")
    Page<User> getAllTeacher(Pageable pageable);
    //* Admin xem tất cả thông tin user trừ chính admin
//    @Query(value = "select u.* from user u join user_role ur on u.id=ur.user_id " +
//            "join role r on ur.role_id=r.id where r.role_name not like 'ROLE_ADMIN'",nativeQuery = true)
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName <> 'ROLE_ADMIN'")
    Page<User> getAllUserExceptAdmin(Pageable pageable);
}