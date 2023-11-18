package com.hcmute.ecommerce.universeshop.base.Authentication;

import com.hcmute.ecommerce.universeshop.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private UserEntity user;

    private String jwtToken;
}
