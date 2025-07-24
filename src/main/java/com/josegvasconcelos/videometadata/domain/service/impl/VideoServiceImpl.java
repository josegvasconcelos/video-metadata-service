package com.josegvasconcelos.videometadata.domain.service.impl;

import com.josegvasconcelos.videometadata.domain.entity.Video;
import com.josegvasconcelos.videometadata.domain.exception.VideoNotFoundException;
import com.josegvasconcelos.videometadata.domain.repository.VideoRepository;
import com.josegvasconcelos.videometadata.domain.service.VideoService;
import com.josegvasconcelos.videometadata.resource.gateway.VideoImportGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VideoServiceImpl implements VideoService {

    private VideoImportGateway videoImportGateway;

    private VideoRepository videoRepository;

    @Override
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
}
