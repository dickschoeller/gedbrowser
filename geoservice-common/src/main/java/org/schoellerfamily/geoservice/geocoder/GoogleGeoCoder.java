package org.schoellerfamily.geoservice.geocoder;

import com.google.maps.GeoApiContext;
import com.google.maps.GeoApiContext.Builder;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Wrapper around Google's Geocoding API.
 *
 * @author Dick Schoeller
 */
@Slf4j
@RequiredArgsConstructor
public final class GoogleGeoCoder implements GeoCoder {
    /** */
    private final String key;

    /**
     * Executes geocode.
     *
     * @param placeName the place name to use
     * @return the resulting geocoding result array
     */
    @Override
    public GeocodingResult[] geocode(final String placeName) {
        log.debug("Querying Google APIs for place: {}", placeName);
        final Builder builder = new GeoApiContext.Builder().apiKey(key);
        try (GeoApiContext context = builder.build()) {
            return GeocodingApi.geocode(context, placeName).await();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return new GeocodingResult[0];
        } catch (Exception e) {
            log.error("Problem querying the place: {}", placeName, e);
            return new GeocodingResult[0];
        }
    }
}
