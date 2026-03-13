package org.schoellerfamily.geoservice.client;

import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * Minimal cache interface used by {@link GeoServiceClient}.
 * Keeping this interface free of ehcache API types allows
 * {@code GeoServiceClient} to load without ehcache on the classpath.
 *
 * @author Dick Schoeller
 */
public interface PlaceCache {
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
}
