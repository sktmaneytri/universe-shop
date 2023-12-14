package com.hcmute.ecommerce.universeshop.role;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RoleEntity> createNewRole(@RequestBody RoleEntity roleEntity) {
        return new ResponseEntity<>(roleService.saveRole(roleEntity), HttpStatus.CREATED);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoleEntity>> getAllRoles () {
        return new ResponseEntity<>(roleService.getRoleEntities(), HttpStatus.OK);
    }

}
