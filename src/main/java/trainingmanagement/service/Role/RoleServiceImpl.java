package trainingmanagement.service.Role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.response.admin.RoleResponse;
import trainingmanagement.model.entity.Enum.ERoles;
import trainingmanagement.model.entity.Role;
import trainingmanagement.repository.RoleRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllToList() {
        return roleRepository.findAll();
    }

    @Override
    public List<RoleResponse> getAllRoleResponsesToList() {
        return getAllToList().stream().map(this::entityMap).toList();
    }

    @Override
    public Role findByRoleName(ERoles roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public List<RoleResponse> findAllByRoleNameContainingIgnoreCase(String roleName) {
        return roleRepository.findAllByRoleNameContainingIgnoreCase(roleName).stream().map(this::entityMap).toList();
    }

    @Override
    public RoleResponse entityMap(Role role) {
        return RoleResponse.builder()
            .roleName(role.getRoleName().name())
            .build();
    }
}
