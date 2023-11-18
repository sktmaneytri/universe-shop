package com.hcmute.ecommerce.universeshop.role;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    RoleEntity saveRole(RoleEntity role);

    RoleEntity getRoleById(String roleName);

    List<RoleEntity> getRoleEntities();
}
