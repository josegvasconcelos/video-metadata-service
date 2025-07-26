package com.josegvasconcelos.videometadata.application.web.documentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ImportVideoRequest",
        description = "Request payload used to import a video"
)
public interface ImportVideoRequestDoc {
    @Schema(
            description = "URL of video that will be imported",
            example = "https://source.com/video-title"
    )
    String url();
}
