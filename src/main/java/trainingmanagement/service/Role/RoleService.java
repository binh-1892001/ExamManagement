package trainingmanagement.service.Role;
import trainingmanagement.model.dto.response.RoleResponse;
import trainingmanagement.model.entity.Enum.ERoles;
import trainingmanagement.model.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> getAllToList();
    List<RoleResponse> getAllRoleResponsesToList();
    Role findByRoleName(ERoles roleName);
    List<RoleResponse> findAllByRoleNameContainingIgnoreCase(String roleName);
    RoleResponse entityMap(Role role);
}
