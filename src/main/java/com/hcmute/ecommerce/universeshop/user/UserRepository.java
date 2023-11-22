package com.hcmute.ecommerce.universeshop.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query("SELECT u FROM users u WHERE u.userName = :userName")
    UserEntity getUserByUsername(@Param("userName") String username);
}