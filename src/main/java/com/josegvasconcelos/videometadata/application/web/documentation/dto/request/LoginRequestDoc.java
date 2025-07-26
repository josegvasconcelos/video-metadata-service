package com.josegvasconcelos.videometadata.application.web.documentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "LoginRequest",
        description = "Request payload used for logging in an User"
)
public interface LoginRequestDoc {
    @Schema(
            description = "User's registered username",
            example = "john.doe"
    )
    String username();
    @Schema(
            description = "User's password hashed using a SHA-256 (Hexadecimal)",
            example = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"
    )
    String password();
}
