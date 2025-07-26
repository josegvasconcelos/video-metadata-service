package com.josegvasconcelos.videometadata.application.web.dto.response;

import com.josegvasconcelos.videometadata.application.web.documentation.dto.response.ErrorResponseDoc;
import org.springframework.http.HttpStatus;

public record ErrorResponseDTO(
        HttpStatus status,
        String message,
        String url
) implements ErrorResponseDoc {}
