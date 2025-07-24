package com.josegvasconcelos.videometadata.domain.service;

import com.josegvasconcelos.videometadata.domain.entity.Video;

public interface VideoService {
    Video importMetadataByUrl(String url);
}
