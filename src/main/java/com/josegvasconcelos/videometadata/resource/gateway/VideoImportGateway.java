package com.josegvasconcelos.videometadata.resource.gateway;

import com.josegvasconcelos.videometadata.domain.entity.Video;

public interface VideoImportGateway {
    Video importVideoMetadataByUrl(String url);
}
