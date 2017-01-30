package org.schoellerfamily.geoservice.geocoder;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

/**
 * Stub version of the Geocoding wrapper. Works with some canned behaviors.
 *
 * @author Dick Schoeller
 */
public final class StubGeoCoder implements GeoCoder {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private final Set<String> unknowns;

    /**
     * @param unknowns list of names that are expected to be unknown
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
     * {@inheritDoc}
     */
    @Override
    public GeocodingResult[] geocode(final String placeName) {
        logger.debug("Stub geocode for: " + placeName);
        if (unknowns.contains(placeName)
                || "XYZZY".equals(placeName)
                || "PLUGH".equals(placeName)
                || "Blah Blah".equals(placeName)) {
            return new GeocodingResult[0];
        }
        GeocodingResult[] results = new GeocodingResult[1];
        results[0] = new GeocodingResult();
        results[0].formattedAddress = placeName;
        results[0].geometry = new Geometry();
        final double lat = 40.65800200;
        final double lng = -75.40644300;
        results[0].geometry.location = new LatLng(lat, lng);
        return results;
    }
}
