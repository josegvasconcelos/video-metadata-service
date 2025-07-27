package com.josegvasconcelos.videometadata.application.web.controller;

import com.josegvasconcelos.videometadata.application.service.AuthenticationService;
import com.josegvasconcelos.videometadata.application.web.documentation.controller.AuthenticationControllerDoc;
import com.josegvasconcelos.videometadata.application.web.dto.request.LoginRequestDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.LoginResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController implements AuthenticationControllerDoc {

    private AuthenticationService authenticationService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequest
    ) {
        log.info("Received a login request for user: {}", loginRequest.username());
        var token = authenticationService.authenticate(loginRequest.username(),  loginRequest.password());

        log.info("Returning an authentication token for for user: {}", loginRequest.username());
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }
}
