package com.josegvasconcelos.videometadata.application.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ImportVideoRequestDTO(
        @NotBlank(message = "URL must not be blank")
        @Pattern(
                regexp = "^https?://([^.]+)\\.com/([^/?#]+)$",
                message = "URL must be in the format https://source.com/video-title"
        )
        String url
) {
}
