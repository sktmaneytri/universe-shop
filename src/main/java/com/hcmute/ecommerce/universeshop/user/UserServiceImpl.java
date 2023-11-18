package com.hcmute.ecommerce.universeshop.user;

import com.hcmute.ecommerce.universeshop.base.exception.Constants;
import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final ErrorMessage errorMessage;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ErrorMessage errorMessage) {
        this.userRepository = userRepository;
        this.errorMessage = errorMessage;
    }

    @Override
    public UserEntity findUserById(Long userId) {
        return null;
    }

    @Override
    public UserEntity findUserProfileByJwt(String jwt) {
        return null;
    }

    @Override
    public User loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByEmail(username);
        if(userEntity == null) {
            throw new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_NOT_FOUND));
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(userEntity.getEmail(), userEntity.getPassword(),authorities);
    }
}
