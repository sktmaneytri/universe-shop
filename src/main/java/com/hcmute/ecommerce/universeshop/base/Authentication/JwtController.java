package com.hcmute.ecommerce.universeshop.base.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {
    @Autowired
    private JwtService jwtService;
    @PostMapping({"/api/v1/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest request) throws Exception {
        return jwtService.createJwtToken(request);
    }
}
