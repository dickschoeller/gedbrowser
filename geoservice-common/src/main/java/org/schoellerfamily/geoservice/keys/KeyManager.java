package org.schoellerfamily.geoservice.keys;

/**
 * @author Dick Schoeller
 */
public interface KeyManager {
    /**
     * Read the Google Geocoding key string.
     *
     * @return the key string
     */
    String getGeocodingKey();

    /**
     * Read the Google Maps key string.
     *
     * @return the key string
     */
    String getMapsKey();
}
