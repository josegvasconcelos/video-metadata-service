package com.josegvasconcelos.videometadata.application.web.documentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

@Schema(
        name = "ErrorResponseDoc",
        description = "Response coming from an API error"
)
public interface ErrorResponseDoc {
    @Schema(
            description = "HTTP Status of the API error",
            example = "BAD_REQUEST"
    )
    HttpStatus status();
    @Schema(
            description = "Description of the API error cause",
            example = "Missing parameter X"
    )
    String message();
    @Schema(
            description = "URL that raised the API error",
            example = "/videos/import"
    )
    String url();
}
