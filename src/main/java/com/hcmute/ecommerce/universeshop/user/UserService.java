package com.hcmute.ecommerce.universeshop.user;

import java.util.List;

public interface UserService {
    UserEntity registerNewUser(UserEntity user);

    void initRolesAndUser();
    List<UserEntity> getUserEntities();
    UserEntity getUserByUserName(String userName);
}
