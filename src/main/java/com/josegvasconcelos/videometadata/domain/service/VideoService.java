package com.josegvasconcelos.videometadata.domain.service;

import com.josegvasconcelos.videometadata.application.web.dto.filter.VideoFilterDTO;
import com.josegvasconcelos.videometadata.domain.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoService {
    Video importMetadataByUrl(String url);
    Video findVideoById(String id);
    Page<Video> findAllVideos(Pageable pageable, VideoFilterDTO filters);
}
