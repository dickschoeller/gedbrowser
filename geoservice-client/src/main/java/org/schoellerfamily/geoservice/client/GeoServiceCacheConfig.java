package org.schoellerfamily.geoservice.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * Configures the geoservice client cache using Spring's cache abstraction and
 * a Caffeine-backed cache manager.
 */
@Configuration
@EnableCaching
@EnableRetry
public class GeoServiceCacheConfig {
    /** Cache name used for geocode lookups. */
    public static final String GEOCODE_CACHE = "geocode";

    /**
     * Creates the cache manager used by {@link GeoServiceClient}.
     *
     * @param maxSize maximum number of entries to retain in the geocode cache
     * @param ttlSeconds time-to-live of each cache entry in seconds
     * @return the configured cache manager
     */
    @Bean
    public CacheManager cacheManager(
            @Value("${geoservice.cache.max-size:1000}") final int maxSize,
            @Value("${geoservice.cache.ttl-seconds:3600}") final long ttlSeconds) {
        final CaffeineCacheManager cacheManager = new CaffeineCacheManager(GEOCODE_CACHE);
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .maximumSize(maxSize)
            .expireAfterWrite(Duration.ofSeconds(ttlSeconds)));
        return cacheManager;
    }
}
