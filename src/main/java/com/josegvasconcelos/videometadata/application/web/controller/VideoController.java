package com.josegvasconcelos.videometadata.application.web.controller;

import com.josegvasconcelos.videometadata.application.web.dto.request.ImportVideoRequestDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.VideoResponseDTO;
import com.josegvasconcelos.videometadata.domain.service.VideoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<VideoResponseDTO> importVideoMetadataByUrl(
            @RequestBody @Valid ImportVideoRequestDTO importVideoRequest
    ) {
        var video = videoService.importMetadataByUrl(importVideoRequest.url());

        var response = VideoResponseDTO.from(video);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<VideoResponseDTO> getVideoById(@PathVariable String id) {
        var video = videoService.findVideoById(id);

        var response = VideoResponseDTO.from(video);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
