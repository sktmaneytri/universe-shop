package com.hcmute.ecommerce.universeshop.base.Authentication;

import lombok.*;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String jwt;
    private String message;

}
