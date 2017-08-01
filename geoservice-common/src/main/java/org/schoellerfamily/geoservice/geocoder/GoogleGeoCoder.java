package org.schoellerfamily.geoservice.geocoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

/**
 * Wrapper around Google's Geocoding API.
 *
 * @author Dick Schoeller
 */
public final class GoogleGeoCoder implements GeoCoder {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
        logger.debug("Querying Google APIs for place: " + placeName);
        final GeoApiContext context =
            new GeoApiContext.Builder().apiKey(key).build();
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, placeName).await();
        } catch (Exception e) {
            logger.error("Problem querying the place: " + placeName, e);
            results = new GeocodingResult[0];
        }
        return results;
    }
}
