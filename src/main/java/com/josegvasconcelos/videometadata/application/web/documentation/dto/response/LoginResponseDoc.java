package com.josegvasconcelos.videometadata.application.web.documentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "LoginResponse",
        description = "Response coming from a successful user login"
)
public interface LoginResponseDoc {
    @Schema(
            description = "A valid JWT token for the authenticated user",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30"
    )
    String token();
}
