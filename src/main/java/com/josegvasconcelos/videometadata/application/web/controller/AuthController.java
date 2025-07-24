package com.josegvasconcelos.videometadata.application.web.controller;

import com.josegvasconcelos.videometadata.application.service.AuthService;
import com.josegvasconcelos.videometadata.application.web.dto.request.LoginRequestDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.LoginResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequest
    ) {
        String username = loginRequest.username();
        String password = loginRequest.password();

        return ResponseEntity.ok(
                new LoginResponseDTO(authService.authenticate(username, password))
        );
    }
}
