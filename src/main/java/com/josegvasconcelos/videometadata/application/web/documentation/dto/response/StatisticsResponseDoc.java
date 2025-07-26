package com.josegvasconcelos.videometadata.application.web.documentation.dto.response;


import com.josegvasconcelos.videometadata.application.web.dto.response.SourceStatisticsResponseDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "StatisticsResponse",
        description = "Response containing video statistics for all video sources"
)
public interface StatisticsResponseDoc {
    @ArraySchema(
            arraySchema = @Schema(
                    description = "List of source statistics",
                    implementation = SourceStatisticsResponseDTO.class
            )
    )
    List<SourceStatisticsResponseDTO> sourcesStatistics();
}
