package com.josegvasconcelos.videometadata.application.web.dto.response;

import com.josegvasconcelos.videometadata.domain.model.SourceStatistics;

public record SourceStatisticsDTO(
        String source,
        Long importedVideos,
        Double averageDuration
) {
    public static SourceStatisticsDTO fromSourceStatistics(SourceStatistics sourceStatistics) {
        return new SourceStatisticsDTO(
                sourceStatistics.source(),
                sourceStatistics.importedVideos(),
                sourceStatistics.averageDuration()
        );
    }
}
