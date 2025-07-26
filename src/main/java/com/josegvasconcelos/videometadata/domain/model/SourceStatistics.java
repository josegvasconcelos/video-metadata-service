package com.josegvasconcelos.videometadata.domain.model;

public record SourceStatistics(
        String source,
        Long importedVideos,
        Double averageDuration
) {
}
