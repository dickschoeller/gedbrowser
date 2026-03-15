package org.schoellerfamily.geoservice.client;

import java.time.Duration;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * Ehcache-backed {@link PlaceCache} implementation.
 * All ehcache API types are confined to this class so that
 * {@link GeoServiceClient} can be loaded without ehcache on the classpath.
 *
 * @author Dick Schoeller
 */
public final class EhcachePlaceCache implements PlaceCache, AutoCloseable {

    /** Ehcache manager; closed when the application context is destroyed. */
    private final CacheManager cacheManager;

    /** The underlying ehcache instance. */
    private final org.ehcache.Cache<String, GeoServiceItem> cache;

    /**
     * @param maxSize    maximum number of entries to hold in memory
     * @param ttlSeconds time-to-live for each entry in seconds
     */
    public EhcachePlaceCache(final int maxSize, final long ttlSeconds) {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("geocode",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                        String.class, GeoServiceItem.class,
                                        ResourcePoolsBuilder.heap(maxSize))
                                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(
                                        Duration.ofSeconds(ttlSeconds)))
                                .build())
                .build(true);
        cache = cacheManager.getCache("geocode", String.class, GeoServiceItem.class);
    }

    /** {@inheritDoc} */
    @Override
    public GeoServiceItem get(final String placeName) {
        return cache.get(placeName);
    }

    /** {@inheritDoc} */
    @Override
    public void put(final String placeName, final GeoServiceItem item) {
        cache.put(placeName, item);
    }

    /**
     * Closes this cache and releases all resources held by the underlying
     * {@link CacheManager}.
     */
    @Override
    public void close() {
        cacheManager.close();
    }
}
