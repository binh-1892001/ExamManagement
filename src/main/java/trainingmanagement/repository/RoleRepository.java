package trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.model.entity.Role;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(ERoleName roleName);
//    @Query(value = "SELECT * FROM role r WHERE r.role_name LIKE CONCAT('%', ?1, '%')", nativeQuery = true)
    @Query("select r from Role r WHERE LOWER(r.roleName) LIKE LOWER(CONCAT('%', :roleName, '%')) ")
    Page<Role> findAllByRoleNameContainingIgnoreCase(String roleName, Pageable pageable);
    //---------------------------------Page----------------------------------------------------
    Page<Role> findAll(Pageable pageable);
}