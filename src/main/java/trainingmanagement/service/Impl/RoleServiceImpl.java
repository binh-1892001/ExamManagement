package trainingmanagement.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingmanagement.model.dto.response.admin.ARoleResponse;
import trainingmanagement.model.enums.ERoleName;
import trainingmanagement.model.entity.Role;
import trainingmanagement.repository.RoleRepository;
import trainingmanagement.service.RoleService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Page<Role> getAllToList(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Page<ARoleResponse> getAllRoleResponsesToList(Pageable pageable) {
        return getAllToList(pageable).map(this::entityAMap);
    }

    @Override
    public Role findByRoleName(ERoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public Page<ARoleResponse> findAllByRoleNameContainingIgnoreCase(String roleName, Pageable pageable) {
        return roleRepository.findAllByRoleNameContainingIgnoreCase(roleName, pageable).map(this::entityAMap);
    }

    @Override
    public ARoleResponse entityAMap(Role role) {
        return ARoleResponse.builder()
                .roleId(role.getId())
                .roleName(role.getRoleName())
                .build();
    }
}
