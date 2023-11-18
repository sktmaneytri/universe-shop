package com.hcmute.ecommerce.universeshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserResource {
    @Autowired
    private UserService userService;
    @PostConstruct
    public void initRolesAndUsers() {
        userService.initRolesAndUser();
    }
    @GetMapping
    public List<UserEntity> getAllUsers() {
       return userService.getUserEntities();
    }

    @PostMapping({"/register"})
    public UserEntity registerNewUser(@RequestBody UserEntity user) {return userService.registerNewUser(user);}
    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('ADMIN')")
    public String forAdmin() {
        return "This URL is only accessible to admin";
    }
    @GetMapping("/forUser")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String forUser() {
        return "This URL is only accessible to user";
    }
}
