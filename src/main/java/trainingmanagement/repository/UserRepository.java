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
    Optional<User> findByUsername(String username);
    Page<User> findAll(Pageable pageable);
    boolean existsByUsername(String username);
    @Query(value = "SELECT * FROM user u where u.username LIKE CONCAT('%', :keyword, '%') OR u.full_name LIKE CONCAT('%', :keyword, '%')"
            , nativeQuery = true)
    List<User> findByUsernameOrFullNameContainingIgnoreCase(String keyword);
}