package com.josegvasconcelos.videometadata.application.web.dto.response;

import com.josegvasconcelos.videometadata.application.web.documentation.dto.response.VideoResponseDoc;
import com.josegvasconcelos.videometadata.domain.entity.Video;

import java.time.LocalDate;

public record VideoResponseDTO(
        String id,
        String title,
        String description,
        String source,
        String url,
        Long durationInSeconds,
        LocalDate uploadDate
) implements VideoResponseDoc {
    public static VideoResponseDTO fromVideo(Video video) {
        return new VideoResponseDTO(
                video.getId(),
                video.getTitle(),
                video.getDescription(),
                video.getSource(),
                video.getUrl(),
                video.getDurationInSeconds(),
                video.getUploadDate()
        );
    }
}
