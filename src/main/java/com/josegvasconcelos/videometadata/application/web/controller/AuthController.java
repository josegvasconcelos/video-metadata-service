package com.josegvasconcelos.videometadata.application.web.controller;

import com.josegvasconcelos.videometadata.application.service.JWTService;
import com.josegvasconcelos.videometadata.application.web.dto.request.LoginRequestDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.LoginResponseDTO;
import com.josegvasconcelos.videometadata.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private UserRepository userRepository;

    private JWTService jwtService;

    private BCryptPasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequest
    ) {
        String username = loginRequest.username();
        String password = loginRequest.password();

        return userRepository.findByUsername(username)
                .filter(user ->
                        encoder.matches(password, user.getPassword())
                )
                .map(user ->
                        ResponseEntity.ok(new LoginResponseDTO(jwtService.generateToken(user.getUsername(), user.getRole())))
                ).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
