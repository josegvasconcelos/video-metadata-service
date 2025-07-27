package com.josegvasconcelos.videometadata.application.web.controller;

import com.josegvasconcelos.videometadata.application.web.documentation.controller.VideoControllerDoc;
import com.josegvasconcelos.videometadata.application.web.dto.filter.VideoFilterDTO;
import com.josegvasconcelos.videometadata.application.web.dto.request.ImportVideoRequestDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.StatisticsResponseDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.VideoResponseDTO;
import com.josegvasconcelos.videometadata.domain.service.VideoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/videos")
public class VideoController implements VideoControllerDoc {

    private VideoService videoService;

    @Override
    @PostMapping("/import")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<VideoResponseDTO> importVideoMetadataByUrl(
            @RequestBody @Valid ImportVideoRequestDTO importVideoRequest
    ) {
        log.info("Received a request to import video metadata by url: {}", importVideoRequest.toString());
        var video = videoService.importMetadataByUrl(importVideoRequest.url());

        var response = VideoResponseDTO.fromVideo(video);

        log.info("Returning a video response for url: {}", video.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<VideoResponseDTO> getVideoById(@PathVariable String id) {
        log.info("Received a request to get video by id: {}", id);
        var video = videoService.findVideoById(id);

        var response = VideoResponseDTO.fromVideo(video);

        log.info("Returning a video response for id: {}", video.getUrl());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping()
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<VideoResponseDTO>> getAllVideos(
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute VideoFilterDTO filters
    ) {
        log.info("Received a request to get all videos for filters: {} and page: {}", filters, pageable);
        var videosPage = videoService.findAllVideos(pageable, filters);

        var response = videosPage.stream()
                .map(VideoResponseDTO::fromVideo)
                .toList();

        log.info("Returning all videos for filters: {} and page: {}", filters, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<StatisticsResponseDTO> getStatistics() {
        log.info("Received a request to get video statistics");
        var statistics = videoService.calculateStatistics();

        var response = StatisticsResponseDTO.fromStatistics(statistics);

        log.info("Returning video statistics response for every source");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
