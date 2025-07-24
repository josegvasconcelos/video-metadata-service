package com.josegvasconcelos.videometadata.application.web.controller;

import com.josegvasconcelos.videometadata.application.web.dto.request.ImportVideoRequestDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.ImportVideoResponseDTO;
import com.josegvasconcelos.videometadata.domain.service.VideoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private VideoService videoService;

    @PostMapping("/import")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ImportVideoResponseDTO> importVideoMetadataByUrl(
            @RequestBody @Valid ImportVideoRequestDTO importVideoRequest
    ) {
        var video = videoService.importMetadataByUrl(importVideoRequest.url());

        var response = new ImportVideoResponseDTO(
                video.getId(),
                video.getTitle(),
                video.getDescription(),
                video.getSource(),
                video.getUrl(),
                video.getDurationInSeconds()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
