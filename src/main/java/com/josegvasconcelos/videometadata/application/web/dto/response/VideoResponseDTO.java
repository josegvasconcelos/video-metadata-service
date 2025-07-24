package com.josegvasconcelos.videometadata.application.web.dto.response;

import com.josegvasconcelos.videometadata.domain.entity.Video;

public record VideoResponseDTO(
        String id,
        String title,
        String description,
        String source,
        String url,
        Long durationInSeconds
) {
    public static VideoResponseDTO from(Video video) {
        return new VideoResponseDTO(
                video.getId(), video.getTitle(), video.getDescription(), video.getSource(), video.getUrl(), video.getDurationInSeconds()
        );
    }
}
