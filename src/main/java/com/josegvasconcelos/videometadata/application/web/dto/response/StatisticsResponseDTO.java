package com.josegvasconcelos.videometadata.application.web.dto.response;

import com.josegvasconcelos.videometadata.application.web.documentation.dto.response.StatisticsResponseDoc;
import com.josegvasconcelos.videometadata.domain.model.Statistics;

import java.util.List;

public record StatisticsResponseDTO(
        List<SourceStatisticsResponseDTO> sourcesStatistics
) implements StatisticsResponseDoc {
    public static StatisticsResponseDTO fromStatistics(Statistics statistics) {
        return new StatisticsResponseDTO(
                statistics.sourcesStatistics()
                        .stream()
                        .map(SourceStatisticsResponseDTO::fromSourceStatistics)
                        .toList()
        );
    }
}
