package com.josegvasconcelos.videometadata.application.web.controller;

import com.josegvasconcelos.videometadata.application.service.AuthenticationService;
import com.josegvasconcelos.videometadata.application.web.dto.request.LoginRequestDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO loginRequest
    ) {
        var token = authenticationService.authenticate(loginRequest.username(),  loginRequest.password());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }
}
