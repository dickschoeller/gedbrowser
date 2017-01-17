package org.schoellerfamily.geoservice.client;

import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * @author Dick Schoeller
 */
public interface GeoServiceClient {
    /**
     * @param placeName the place name
     * @return the geocoding result
     */
    GeoServiceItem get(String placeName);
}
