package com.hcmute.ecommerce.universeshop.role;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleResource {

    private final RoleService roleService;

    @PostMapping()
    public RoleEntity createNewRole(@RequestBody RoleEntity roleEntity) {
        return roleService.saveRole(roleEntity);
    }

    @GetMapping()
    public List<RoleEntity> getAllRoles () {
        return roleService.getRoleEntities();
    }

}
