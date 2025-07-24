package com.josegvasconcelos.videometadata.application.web.dto.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record VideoFilterDTO(
        String source,
        Long durationInSeconds,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate uploadDate
) {
}
