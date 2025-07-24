package com.josegvasconcelos.videometadata.application.service;

import com.josegvasconcelos.videometadata.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private AuthenticationManager authenticationManager;

    private JWTService jwtService;

    public String authenticate(String username, String password) {
        var credentials =  new UsernamePasswordAuthenticationToken(username, password);
        var authentication = authenticationManager.authenticate(credentials);

        return jwtService.generateToken((User) authentication.getPrincipal());
    }
}
