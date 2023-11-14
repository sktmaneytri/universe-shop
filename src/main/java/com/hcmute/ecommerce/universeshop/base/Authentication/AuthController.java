//package com.hcmute.ecommerce.universeshop.base.Authentication;
//
//import com.hcmute.ecommerce.universeshop.base.config.JwtProvider;
//import com.hcmute.ecommerce.universeshop.base.exception.BadCredentialExeception;
//import com.hcmute.ecommerce.universeshop.base.exception.Constants;
//import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
//import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
//import com.hcmute.ecommerce.universeshop.user.UserEntity;
//import com.hcmute.ecommerce.universeshop.user.UserRepository;
//import com.hcmute.ecommerce.universeshop.user.UserService;
//import com.hcmute.ecommerce.universeshop.user.UserServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//    private final UserRepository userRepository;
//    private final UserService userService;
//    private final ErrorMessage errorMessage;
//    private final JwtProvider jwtProvider;
//    private final PasswordEncoder passwordEncoder;
//
//
//    @Autowired
//    public AuthController(UserRepository userRepository, UserService userService, ErrorMessage errorMessage, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.userService = userService;
//        this.errorMessage = errorMessage;
//        this.jwtProvider = jwtProvider;
//        this.passwordEncoder = passwordEncoder;
//    }
//    @PostMapping("/signup")
//    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserEntity user) throws ResourceNotFoundException {
//        String email = user.getEmail();
//        String password = user.getPassword();
//        String firstString = user.getFirstName();
//        String lastNString = user.getLastName();
//
//        UserEntity isEmailExist = userRepository.findByEmail(email);
//        if(isEmailExist!= null) {
//            throw new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_EMAIL_EXISTED));
//        }
//        UserEntity createdUser = new UserEntity();
//        createdUser.setEmail(email);
//        createdUser.setPassword(passwordEncoder.encode(password));
//        createdUser.setFirstName(firstString);
//        UserEntity savedUser = userRepository.save(createdUser);
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String token = jwtProvider.generateToken(authentication);
//
//        AuthResponse authResponse = new AuthResponse();
//        authResponse.setJwt(token);
//        authResponse.setMessage("Signup Success");
//
//
//        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
//    }
//    @PostMapping("/signin")
//    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) throws SecurityException {
//        String username = loginRequest.getEmail();
//        String password = loginRequest.getPassword();
//
//        Authentication authentication = authenticate(username, password);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String token = jwtProvider.generateToken(authentication);
//        AuthResponse authResponse = new AuthResponse();
//        authResponse.setJwt(token);
//        authResponse.setMessage("Signin Success");
//        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
//    }
//
//    private Authentication authenticate(String username, String password) throws SecurityException {
//        UserDetails userDetails = userService.loadUserByUsername(username);
//        if(userDetails == null) {
//            throw new BadCredentialExeception(errorMessage.getMessage(Constants.USERNAME_IS_INVALID));
//        }
//        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
//            throw new BadCredentialExeception(errorMessage.getMessage(Constants.USER_PASSWORD_INVALID));
//        }
//        return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
//    }
//}
