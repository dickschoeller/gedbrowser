package org.schoellerfamily.geoservice.geocoder;

import com.google.maps.model.GeocodingResult;

/**
 * Defines the contract for geo coder.
 *
 * @author Richard Schoeller
 */
public interface GeoCoder {
    /**
     * Request the geocode from Google.
     *
     * @param placeName the place name to find
     * @return the geo-coding result
     */
    GeocodingResult[] geocode(String placeName);
}
