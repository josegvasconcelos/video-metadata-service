package com.josegvasconcelos.videometadata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class VideoMetadataApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoMetadataApplication.class, args);
    }
}