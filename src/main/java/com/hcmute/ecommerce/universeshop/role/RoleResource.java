package com.hcmute.ecommerce.universeshop.role;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleResource {

    private final RoleService roleService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public RoleEntity createNewRole(@RequestBody RoleEntity roleEntity) {
        return roleService.saveRole(roleEntity);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleEntity> getAllRoles () {
        return roleService.getRoleEntities();
    }

}
