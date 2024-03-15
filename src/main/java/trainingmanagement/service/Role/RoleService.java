package trainingmanagement.service.Role;
import trainingmanagement.model.dto.response.admin.RoleResponse;
import trainingmanagement.model.entity.Enum.ERoleName;
import trainingmanagement.model.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> getAllToList();
    List<RoleResponse> getAllRoleResponsesToList();
    Role findByRoleName(ERoleName roleName);
    List<RoleResponse> findAllByRoleNameContainingIgnoreCase(String roleName);
    RoleResponse entityMap(Role role);
}
