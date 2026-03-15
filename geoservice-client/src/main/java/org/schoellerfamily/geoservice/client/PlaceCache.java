package org.schoellerfamily.geoservice.client;

import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * Minimal cache interface used by {@link GeoServiceClient}.
 * Keeping this interface free of ehcache API types allows
 * {@code GeoServiceClient} later substitution of a different cache implementation.
 * Implementations that hold resources should override {@link #close()}.
 *
 * @author Dick Schoeller
 */
public interface PlaceCache extends AutoCloseable {
    /**
     * @param placeName the place name key
     * @return the cached item, or {@code null} if not present
     */
    GeoServiceItem get(String placeName);

    /**
     * @param placeName the place name key
     * @param item      the item to cache
     */
    void put(String placeName, GeoServiceItem item);

    /**
     * Releases any resources held by this cache.
     * The default implementation is a no-op; override in implementations
     * that hold resources such as a {@code CacheManager}.
     */
    @Override
    default void close() {
        // no-op by default
    }
}
