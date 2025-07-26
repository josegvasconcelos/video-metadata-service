package com.josegvasconcelos.videometadata.application.web.dto.request;

import com.josegvasconcelos.videometadata.application.web.documentation.dto.request.LoginRequestDoc;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank(message = "Username must not be blank")
        @Size(max = 100, message = "Username must be at most 100 characters")
        String username,
        @NotBlank(message = "Password must not be blank")
        @Pattern(
                regexp = "^[A-Fa-f0-9]{64}$",
                message = "Password must be a SHA-256 hash in hexadecimal format (64 characters)"
        )
        String password
) implements LoginRequestDoc {}
