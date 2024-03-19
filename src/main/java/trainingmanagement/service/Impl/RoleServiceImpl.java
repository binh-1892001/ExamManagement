package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.response.admin.ARoleResponse;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.model.entity.Role;
import trainingmanagement.repository.RoleRepository;
import trainingmanagement.service.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllToList() {
        return roleRepository.findAll();
    }

    @Override
    public List<ARoleResponse> getAllRoleResponsesToList() {
        return getAllToList().stream().map(this::entityMap).toList();
    }

    @Override
    public Role findByRoleName(ERoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public List<ARoleResponse> findAllByRoleNameContainingIgnoreCase(String roleName) {
        return roleRepository.findAllByRoleNameContainingIgnoreCase(roleName).stream().map(this::entityMap).toList();
    }

    //    *********************************************entityMap*********************************************

    @Override
    public ARoleResponse entityMap(Role role) {
        return ARoleResponse.builder()
            .roleName(role.getRoleName())
            .build();
    }
}
