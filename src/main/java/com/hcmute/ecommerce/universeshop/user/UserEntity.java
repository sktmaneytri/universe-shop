package com.hcmute.ecommerce.universeshop.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.ecommerce.universeshop.cart.CartEntity;
import com.hcmute.ecommerce.universeshop.role.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
    private String address;
    @Column(name = "contact_number")
    private String contactNumber;
    @NotNull
    private Boolean activated;
    @Column(name = "verification_code")
    private String verificationCode;
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
    @OneToOne()
    @JsonIgnore
    private CartEntity cart;
}
