package org.schoellerfamily.geoservice.geocoder;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import lombok.extern.slf4j.Slf4j;

/**
 * Wrapper around Google's Geocoding API.
 *
 * @author Dick Schoeller
 */
@Slf4j
public final class GoogleGeoCoder implements GeoCoder {

    /** */
    private final String key;

    /**
     * @param key key string for Google geocoder
     */
    public GoogleGeoCoder(final String key) {
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeocodingResult[] geocode(final String placeName) {
        log.debug("Querying Google APIs for place: {}", placeName);
        final GeoApiContext context =
            new GeoApiContext.Builder().apiKey(key).build();
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, placeName).await();
        } catch (Exception e) {
            log.error("Problem querying the place: {}", placeName, e);
            results = new GeocodingResult[0];
        }
        return results;
    }
}
