package org.schoellerfamily.geoservice.geocoder;

import java.util.HashSet;
import java.util.Set;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

import lombok.extern.slf4j.Slf4j;

/**
 * Stub version of the Geocoding wrapper. Works with some canned behaviors.
 *
 * @author Dick Schoeller
 */
@Slf4j
public final class StubGeoCoder implements GeoCoder {

    /** */
    private final Set<String> unknowns;

    /**
     * Creates a new StubGeoCoder.
     *
     * @param unknowns the unknowns
     */
    @SuppressWarnings("PMD.UseVarargs")
    public StubGeoCoder(final String[] unknowns) {
        this.unknowns = new HashSet<>();
        if (unknowns != null) {
            for (final String unknown : unknowns) {
                this.unknowns.add(unknown);
            }
        }
    }

    /**
     * Executes geocode.
     *
     * @param placeName the place name to use
     * @return the resulting geocoding result array
     */
    @Override
    public GeocodingResult[] geocode(final String placeName) {
        log.debug("Stub geocode for: {}", placeName);
        if (unknowns.contains(placeName)
                || "XYZZY".equals(placeName)
                || "PLUGH".equals(placeName)
                || "Blah Blah".equals(placeName)) {
            return new GeocodingResult[0];
        }
        final GeocodingResult[] results = new GeocodingResult[1];
        results[0] = new GeocodingResult();
        results[0].formattedAddress = placeName;
        results[0].geometry = new Geometry();
        final double lat = 40.65800200;
        final double lng = -75.40644300;
        results[0].geometry.location = new LatLng(lat, lng);
        return results;
    }
}
