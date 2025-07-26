package com.josegvasconcelos.videometadata.domain.model;

import java.util.List;

public record Statistics(
    List<SourceStatistics> sourcesStatistics
) {
}
