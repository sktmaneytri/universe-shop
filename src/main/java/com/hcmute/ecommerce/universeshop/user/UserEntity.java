package com.hcmute.ecommerce.universeshop.user;

import com.hcmute.ecommerce.universeshop.role.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Data
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    private String userName;
    private String userFirstName;
    private String userPassword;
    private String userLastName;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
    joinColumns = {
            @JoinColumn(name = "USER_ID")
    },
            inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<RoleEntity> roles;
}
