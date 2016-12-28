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
public final class GeoCoder {
    // TODO Make an interface of this
    // Requires hiding the Google specific response
    // Requires pushing key management down into the Google stuff

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private final String key;

    /**
     * @param key key string for Google geocoder
     */
    public GeoCoder(final String key) {
        this.key = key;
    }

    /**
     * Request the geocode from Google.
     *
     * @param placeName the place name to find
     * @return the geo-coding result
     */
    public GeocodingResult[] geocode(final String placeName) {
        logger.debug("Querying Google APIs for place: " + placeName);
        final GeoApiContext context = new GeoApiContext().setApiKey(key);
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
