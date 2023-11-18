package com.hcmute.ecommerce.universeshop.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {
    @Id
    private String roleName;
    private String roleDescription;
}
