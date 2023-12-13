package com.hcmute.ecommerce.universeshop.base.Authentication;

import com.hcmute.ecommerce.universeshop.base.exception.*;
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ErrorMessage errorMessage;
    @Autowired
    private AuthenticationManager authenticationManager;


    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws SystemException, AuthorizationException {
        try {
            String userName = jwtRequest.getUserName();
            String userPassword = jwtRequest.getUserPassword();
            authenticate(userName, userPassword);

            final UserDetails userDetails = loadUserByUsername(userName);
            String newGeneratedToken = jwtUtils.generateToken(userDetails);

            UserEntity user = userRepository.findById(userName).get();
            return new JwtResponse(user, newGeneratedToken);
        }catch (SystemException e) {
            throw new SystemException(errorMessage.getMessage(Constants.TOKEN_CAN_NOT_GENERATE));
        }catch (AuthorizationException e) {
            throw new SystemException(errorMessage.getMessage(Constants.NOT_PERMITTED));
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepository.findById(username);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            return new User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthorities(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found for username: " + username);
        }
    }

    private Set getAuthorities(UserEntity user){
        Set authorities = new HashSet();

        user.getRoles().forEach(
                role -> {authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
                });
        return authorities;
    }
    private void authenticate(String userName, String userPassword) throws AuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
            UserEntity userEntity = userRepository.getUserByUsername(userName).get();
            if(userEntity.getActivated() == false) {
                throw new AuthenticationException(errorMessage.getMessage(Constants.USER_IS_DISABLED));
            }
        } catch (DisabledException e) {
            throw new AuthenticationException(errorMessage.getMessage(Constants.USER_IS_DISABLED));
        }catch (NoSuchElementException e) {
            throw new AuthenticationException(errorMessage.getMessage(Constants.USER_IS_DISABLED));
        } catch (BadCredentialsException e) {
            throw new AuthenticationException(errorMessage.getMessage(Constants.EMAIL_PASSWORD_INVALID));
        }
    }
}
