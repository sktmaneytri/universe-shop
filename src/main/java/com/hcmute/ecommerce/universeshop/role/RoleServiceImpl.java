package com.hcmute.ecommerce.universeshop.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleEntity saveRole(RoleEntity role) {
        return roleRepository.save(role);
    }

    @Override
    public RoleEntity getRoleById(String roleName) {
        return roleRepository.findById(roleName).get();
    }

    @Override
    public List<RoleEntity> getRoleEntities() {
        return roleRepository.findAll();
    }
}
