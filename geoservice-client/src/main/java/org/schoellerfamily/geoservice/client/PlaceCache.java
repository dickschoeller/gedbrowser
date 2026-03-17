package org.schoellerfamily.geoservice.client;

import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * Defines the contract for place cache.
 *
 * @author Richard Schoeller
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
