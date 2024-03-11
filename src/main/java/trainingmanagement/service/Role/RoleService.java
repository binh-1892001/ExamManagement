package trainingmanagement.service.Role;

import trainingmanagement.model.entity.Enum.ERoles;
import trainingmanagement.model.entity.Role;

import java.util.List;

public interface RoleService {
    Role findByRoleName(ERoles name);
    List<Role> getAll();
}
