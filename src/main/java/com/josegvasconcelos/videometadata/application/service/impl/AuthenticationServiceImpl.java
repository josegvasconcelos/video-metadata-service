package com.josegvasconcelos.videometadata.application.service.impl;

import com.josegvasconcelos.videometadata.application.service.AuthenticationService;
import com.josegvasconcelos.videometadata.application.service.JWTService;
import com.josegvasconcelos.videometadata.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;

    private JWTService jwtService;

    @Override
    public String authenticate(String username, String password) {
        var credentials =  new UsernamePasswordAuthenticationToken(username, password);
        var authentication = authenticationManager.authenticate(credentials);

        return jwtService.generateToken((User) authentication.getPrincipal());
    }
}
