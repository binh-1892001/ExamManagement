package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Enum.ERoles;
import trainingmanagement.model.entity.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(ERoles roleName);
    @Query(value = "SELECT * FROM role r WHERE r.role_name LIKE CONCAT('%', ?1, '%')", nativeQuery = true)
    List<Role> findAllByRoleNameContainingIgnoreCase(String roleName);
}