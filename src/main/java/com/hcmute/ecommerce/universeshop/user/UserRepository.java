package com.hcmute.ecommerce.universeshop.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query("SELECT u FROM users u WHERE u.userName = :userName AND u.isDeleted = false")
    Optional<UserEntity> getUserByUsername(@Param("userName") String username);


    @Query("SELECT u FROM users u WHERE u.isDeleted = false")
    List<UserEntity> getDeletedUser();
}