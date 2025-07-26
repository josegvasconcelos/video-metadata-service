package com.josegvasconcelos.videometadata.application.configuration.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        var cacheManager = new CaffeineCacheManager("stats");

        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .maximumSize(1000));

        return cacheManager;
    }
}
