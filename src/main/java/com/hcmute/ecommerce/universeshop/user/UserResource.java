package com.hcmute.ecommerce.universeshop.user;

import com.hcmute.ecommerce.universeshop.base.exception.InputValidationException;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserResource {
    @Autowired
    private UserService userService;
    @PostConstruct
    public void initRolesAndUsers() {
        userService.initRolesAndUser();
    }
    @GetMapping("/users")
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


    @PostMapping("/activate")
    public ResponseEntity<String> verifyAndActivateUser(
            @RequestParam("email") String email,
            @RequestParam("verificationCode") String verificationCode) {
        try {
            userService.verifyAndActivateUser(email, verificationCode);
            return ResponseEntity.ok("User activated successfully");
        } catch (InputValidationException e) {
            log.error("Error activating user: " + e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            log.error("Error activating user: " + e.getMessage(), e);
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error activating user: " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
