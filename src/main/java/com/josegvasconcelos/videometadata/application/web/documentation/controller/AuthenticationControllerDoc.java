package com.josegvasconcelos.videometadata.application.web.documentation.controller;

import com.josegvasconcelos.videometadata.application.web.dto.request.LoginRequestDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.ErrorResponseDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

@Tag(
        name = "User Authentication Controller",
        description = "Responsible for authenticating users"
)
@Validated
public interface AuthenticationControllerDoc {

    @Operation(
            summary = "Authenticate user",
            description = "Validates username and password and returns a JWT token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                            content = @Content(
                                    schema = @Schema(implementation = LoginResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Username or password out of format",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    name = "Bad Request",
                                                    value = """
                                                            {
                                                                "status": "BAD_REQUEST",
                                                                "message": "Password must be a SHA-256 hash in hexadecimal format (64 characters)",
                                                                "url": "/auth/login"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Incorrect username or password",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    name = "Unauthorized",
                                                    value = """
                                                            {
                                                                "status": "UNAUTHORIZED",
                                                                "message": "Invalid username or password",
                                                                "url": "/auth/login"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
            }
    )
    ResponseEntity<LoginResponseDTO> login(@Valid LoginRequestDTO loginRequest);
}
