package com.hcmute.ecommerce.universeshop.user;

import java.util.List;

public interface UserService {
    UserEntity registerNewUser(UserEntity user);

    void initRolesAndUser();
    List<UserEntity> getUserEntities();
    UserEntity getUserByUserName(String userName);
    void verifyAndActivateUser(String email, String verificationCode);

    void deleteUser(String userName);
    void reviveUser(String userName);
    List<UserEntity> getUsersDelete();
}
