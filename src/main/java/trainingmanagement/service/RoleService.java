package trainingmanagement.service;
import trainingmanagement.model.dto.response.admin.ARoleResponse;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.model.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> getAllToList();
    List<ARoleResponse> getAllRoleResponsesToList();
    Role findByRoleName(ERoleName roleName);
    List<ARoleResponse> findAllByRoleNameContainingIgnoreCase(String roleName);
    ARoleResponse entityMap(Role role);
}
