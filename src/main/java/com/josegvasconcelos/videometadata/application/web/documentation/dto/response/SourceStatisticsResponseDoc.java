package com.josegvasconcelos.videometadata.application.web.documentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "SourceStatisticsResponse",
        description = "Response containing video statistics for a video source"
)
public interface SourceStatisticsResponseDoc {
    @Schema(
            description = "Video source from where statistics were calculated",
            example = "YOUTUBE"
    )
    String source();

    @Schema(
            description = "Number of videos that were imported for the source",
            example = "50"
    )
    Long importedVideos();

    @Schema(
            description = "Average duration of source's videos in seconds",
            example = "350"
    )
    Double averageDuration();
}
