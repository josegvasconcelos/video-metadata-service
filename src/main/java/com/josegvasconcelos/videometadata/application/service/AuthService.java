package com.josegvasconcelos.videometadata.application.service;

import com.josegvasconcelos.videometadata.application.exception.InvalidCredentialsException;
import com.josegvasconcelos.videometadata.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;

    private JWTService jwtService;

    private BCryptPasswordEncoder encoder;

    public String authenticate(String username, String password){
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new InvalidCredentialsException("Invalid username or password")
        );

        if (encoder.matches(password, user.getPassword())) {
            return jwtService.generateToken(user.getUsername(), user.getRole());
        } else  {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }
}
