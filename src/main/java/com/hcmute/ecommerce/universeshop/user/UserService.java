package com.hcmute.ecommerce.universeshop.user;

import org.springframework.security.core.userdetails.User;

public interface UserService {
    public UserEntity findUserById(Long userId);
    public UserEntity findUserProfileByJwt(String jwt);

    public User loadUserByUsername(String username);


}
