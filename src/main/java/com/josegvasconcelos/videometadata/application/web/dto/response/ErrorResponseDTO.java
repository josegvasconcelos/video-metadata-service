package com.josegvasconcelos.videometadata.application.web.dto.response;

import org.springframework.http.HttpStatus;

public record ErrorResponseDTO(
        HttpStatus status,
        String message,
        String url
) {
}
