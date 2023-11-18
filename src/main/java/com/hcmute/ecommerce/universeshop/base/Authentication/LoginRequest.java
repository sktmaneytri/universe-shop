package com.hcmute.ecommerce.universeshop.base.Authentication;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
