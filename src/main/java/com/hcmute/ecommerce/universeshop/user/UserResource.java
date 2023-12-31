package com.hcmute.ecommerce.universeshop.user;

import com.hcmute.ecommerce.universeshop.base.exception.InputValidationException;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
//    @PostConstruct
//    public void initRolesAndUsers() {
//        userService.initRolesAndUser();
//    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return new ResponseEntity<>(userService.getUserEntities(), HttpStatus.OK);
    }
    @PostMapping({"/register"})
    public ResponseEntity<UserEntity> registerNewUser(@RequestBody UserEntity user) {
        return new ResponseEntity<>(userService.registerNewUser(user), HttpStatus.CREATED);
    }
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
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("Delete successfully!", HttpStatus.NO_CONTENT);
    }
    @PostMapping({"/{id}/revive"})
    public ResponseEntity<String> reviveUser(@PathVariable String id) {
        userService.reviveUser(id);
        return new ResponseEntity<>("Reset user successfully!", HttpStatus.OK);
    }
}
