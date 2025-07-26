package com.josegvasconcelos.videometadata.domain.service.impl;

import com.josegvasconcelos.videometadata.application.web.dto.filter.VideoFilterDTO;
import com.josegvasconcelos.videometadata.domain.entity.Video;
import com.josegvasconcelos.videometadata.domain.exception.VideoNotFoundException;
import com.josegvasconcelos.videometadata.domain.model.SourceStatistics;
import com.josegvasconcelos.videometadata.domain.model.Statistics;
import com.josegvasconcelos.videometadata.domain.repository.VideoRepository;
import com.josegvasconcelos.videometadata.domain.service.VideoService;
import com.josegvasconcelos.videometadata.domain.util.VideoSpecification;
import com.josegvasconcelos.videometadata.resource.gateway.VideoImportGateway;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class VideoServiceImpl implements VideoService {

    private VideoImportGateway videoImportGateway;

    private VideoRepository videoRepository;

    @Override
    @CacheEvict(value = "stats", allEntries = true)
    public Video importMetadataByUrl(String url) {
        var video = videoImportGateway.importVideoMetadataByUrl(url);

        return videoRepository.save(video);
    }

    @Override
    public Video findVideoById(String id) {
        return videoRepository.findById(id).orElseThrow(
                () -> new VideoNotFoundException("Video with id " + id + " not found")
        );
    }

    @Override
    public Page<Video> findAllVideos(Pageable pageable, VideoFilterDTO filters) {
        var specification = VideoSpecification.withFilters(filters);

        return videoRepository.findAll(specification, pageable);
    }

    @Override
    @Cacheable("stats")
    public Statistics calculateStatistics() {
        @Getter
        @Service
        @AllArgsConstructor
        class Stats {
            private Long importedVideos;
            private Long totalDuration;
        }

        var videosBySource = new HashMap<String, Stats>();

        var pageSize = 500;
        Pageable page = PageRequest.of(0, pageSize);
        Page<Video> chunk;

        do {
            chunk = videoRepository.findAll(page);

            chunk.getContent().forEach(video -> {
                if (videosBySource.containsKey(video.getSource())) {
                    var stats = videosBySource.get(video.getSource());

                    stats.importedVideos++;
                    stats.totalDuration += video.getDurationInSeconds();
                } else {
                    videosBySource.put(video.getSource(), new Stats(1L, video.getDurationInSeconds()));
                }
            });

            page = chunk.nextPageable();
        } while (chunk.hasNext());

        var sourcesStatistics = new ArrayList<SourceStatistics>();

        videosBySource.forEach((source, stats) -> {
            var averageDuration = Double.valueOf(stats.totalDuration.doubleValue() / stats.importedVideos.doubleValue());

            sourcesStatistics.add(new SourceStatistics(source, stats.importedVideos, averageDuration));
        });

        return new Statistics(sourcesStatistics);
    }
}
