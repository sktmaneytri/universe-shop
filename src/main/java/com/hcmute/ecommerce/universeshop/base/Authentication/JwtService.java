package com.hcmute.ecommerce.universeshop.base.Authentication;

import com.hcmute.ecommerce.universeshop.user.UserEntity;
import com.hcmute.ecommerce.universeshop.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    public JwtService(UserRepository userRepository, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
//        this.userRepository = userRepository;
//        this.jwtUtils = jwtUtils;
//        this.authenticationManager = authenticationManager;
//    }

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userName, userPassword);

        final UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtils.generateToken(userDetails);

        UserEntity user = userRepository.findById(userName).get();
        return new JwtResponse(user, newGeneratedToken);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(username).get();

        if(user != null) {
            return new User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthorities(user)
            );
        } else {
            throw new UsernameNotFoundException("Username is not valid!");
        }
    }

    private Set getAuthorities(UserEntity user){
        Set authorities = new HashSet();

        user.getRoles().forEach(
                role -> {authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
                });
        return authorities;
    }
    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("User is disabled!");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials from user!");
        }
    }
}
