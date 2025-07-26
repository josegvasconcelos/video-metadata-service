package com.josegvasconcelos.videometadata.application.web.dto.response;

import com.josegvasconcelos.videometadata.application.web.documentation.dto.response.SourceStatisticsResponseDoc;
import com.josegvasconcelos.videometadata.domain.model.SourceStatistics;

public record SourceStatisticsResponseDTO(
        String source,
        Long importedVideos,
        Double averageDuration
) implements SourceStatisticsResponseDoc {
    public static SourceStatisticsResponseDTO fromSourceStatistics(SourceStatistics sourceStatistics) {
        return new SourceStatisticsResponseDTO(
                sourceStatistics.source(),
                sourceStatistics.importedVideos(),
                sourceStatistics.averageDuration()
        );
    }
}
