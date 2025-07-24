package com.josegvasconcelos.videometadata.application.web.dto.response;

public record ImportVideoResponseDTO(
        String id,
        String title,
        String description,
        String source,
        String url,
        Long durationInSeconds
) {
}
