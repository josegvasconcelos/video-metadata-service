package com.josegvasconcelos.videometadata.application.web.dto.response;

import com.josegvasconcelos.videometadata.domain.model.Statistics;

import java.util.List;

public record StatisticsDTO(
        List<SourceStatisticsDTO> sourcesStatistics
) {
    public static StatisticsDTO fromStatistics(Statistics statistics) {
        return new StatisticsDTO(
                statistics.sourcesStatistics()
                        .stream()
                        .map(SourceStatisticsDTO::fromSourceStatistics)
                        .toList()
        );
    }
}
