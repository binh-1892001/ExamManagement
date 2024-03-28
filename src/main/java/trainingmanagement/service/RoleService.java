package trainingmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trainingmanagement.model.dto.response.admin.ARoleResponse;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.model.entity.Role;
import java.util.List;

public interface RoleService {
    Page<Role> getAllToList(Pageable pageable);
    Page<ARoleResponse> getAllRoleResponsesToList(Pageable pageable);
    Role findByRoleName(ERoleName roleName);
    Page<ARoleResponse> findAllByRoleNameContainingIgnoreCase(String roleName, Pageable pageable);
    ARoleResponse entityAMap(Role role);
}