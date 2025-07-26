package com.josegvasconcelos.videometadata.application.web.documentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(
        name = "VideoResponse",
        description = "Response containing video information"
)
public interface VideoResponseDoc {
    @Schema(
            description = "Video's unique ID (ULID)",
            example = "01K147N984DVEN875T3FT2H7TR"
    )
    String id();
    @Schema(
            description = "Video's title",
            example = "Incredible Highlights"
    )
    String title();
    @Schema(
            description = "Video's description",
            example = "The most Incredible Highlights of 2025"
    )
    String description();
    @Schema(
            description = "Source where the video was imported from",
            example = "YOUTUBE"
    )
    String source();
    @Schema(
            description = "URL used to import the video",
            example = "https://source.com/video-title"
    )
    String url();
    @Schema(
            description = "Duration of video in seconds",
            example = "350"
    )
    Long durationInSeconds();
    @Schema(
            description = "Date when the video was uploaded",
            example = "2023-10-27"
    )
    LocalDate uploadDate();
}
