package com.hcmute.ecommerce.universeshop.base.Authentication;

import com.hcmute.ecommerce.universeshop.base.config.JwtProvider;
import com.hcmute.ecommerce.universeshop.base.exception.Constants;
import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import com.hcmute.ecommerce.universeshop.user.UserEntity;
import com.hcmute.ecommerce.universeshop.user.UserRepository;
import com.hcmute.ecommerce.universeshop.user.UserService;
import com.hcmute.ecommerce.universeshop.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ErrorMessage errorMessage;
    private JwtProvider jwtProvider;
    @Autowired
    public AuthController(UserRepository userRepository, UserService userService, ErrorMessage errorMessage, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.errorMessage = errorMessage;
        this.jwtProvider = jwtProvider;
    }

    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserEntity user) throws ResourceNotFoundException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstString = user.getFirstName();
        String lastNString = user.getLastName();

        UserEntity isEmailExist = userRepository.findByEmail(email);
        if(isEmailExist!= null) {
            throw new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_EMAIL_EXISTED));
        }
        UserEntity createdUser = new UserEntity();
        createdUser.setEmail(email);
        createdUser.setPassword(password);
        createdUser.setFirstName(firstString);
        UserEntity savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);


    }
}
