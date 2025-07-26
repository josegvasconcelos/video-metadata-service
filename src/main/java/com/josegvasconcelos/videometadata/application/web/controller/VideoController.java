package com.josegvasconcelos.videometadata.application.web.controller;

import com.josegvasconcelos.videometadata.application.web.dto.filter.VideoFilterDTO;
import com.josegvasconcelos.videometadata.application.web.dto.request.ImportVideoRequestDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.StatisticsResponseDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.VideoResponseDTO;
import com.josegvasconcelos.videometadata.domain.service.VideoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

        var response = VideoResponseDTO.fromVideo(video);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<VideoResponseDTO> getVideoById(@PathVariable String id) {
        var video = videoService.findVideoById(id);

        var response = VideoResponseDTO.fromVideo(video);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<VideoResponseDTO>> getAllVideos(
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute VideoFilterDTO filters
    ) {
        var videosPage = videoService.findAllVideos(pageable, filters);

        var response = videosPage.stream()
                .map(VideoResponseDTO::fromVideo)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<StatisticsResponseDTO> getStatistics() {
        var statistics = videoService.calculateStatistics();

        var response = StatisticsResponseDTO.fromStatistics(statistics);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
