package org.schoellerfamily.geoservice.geocoder;

import com.google.maps.model.GeocodingResult;

/**
 * This interface defines the standard wrapper around a geocoder. The current
 * presumed behavior is to return a result in Google's Geocoding API format.
 *
 * @author Dick Schoeller
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
