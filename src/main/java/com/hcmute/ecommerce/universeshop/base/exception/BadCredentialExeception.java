package com.hcmute.ecommerce.universeshop.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BadCredentialExeception extends RuntimeException {
    public BadCredentialExeception(String message) {
        super(message);
    }
}
