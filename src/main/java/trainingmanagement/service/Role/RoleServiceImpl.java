package trainingmanagement.service.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainingmanagement.model.entity.Enum.ERoles;
import trainingmanagement.model.entity.Role;
import trainingmanagement.repository.RoleRepository;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findByRoleName(ERoles name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

}
