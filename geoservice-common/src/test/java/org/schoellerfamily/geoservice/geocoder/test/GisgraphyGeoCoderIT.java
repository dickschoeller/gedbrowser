package org.schoellerfamily.geoservice.geocoder.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.geocoder.GisgraphyGeoCoder;

import com.google.maps.model.GeocodingResult;

/**
 * Integration tests for {@link GisgraphyGeoCoder}.
 */
final class GisgraphyGeoCoderIT {
    /** */
    private static final String KNOWN_PLACE = "Bethlehem, Pennsylvania";

    /**
     * Verifies that host-only URL configuration can geocode through the default
     * Gisgraphy geocoding endpoint path.
     */
    @Test
    void hostOnlyUrlShouldGeocodeUsingDefaultEndpointPath() {
        final GisgraphyGeoCoder geoCoder = new GisgraphyGeoCoder("http://services.gisgraphy.com");
        final GeocodingResult[] results = assertTimeoutPreemptively(
                Duration.ofSeconds(20),
                () -> geoCoder.geocode(KNOWN_PLACE));

        assertNotNull(results, "Result array should never be null");
        assertSuccessfulResultShapeIfPresent(results);
    }

    /**
     * Verifies that null URL configuration falls back to the built-in default
     * Gisgraphy URL.
     */
    @Test
    @Disabled("Rate limited in free access so it is disabled by default")
    void nullUrlShouldUseBuiltInDefaultGisgraphyUrl() {
        final GisgraphyGeoCoder geoCoder = new GisgraphyGeoCoder(null);
        final GeocodingResult[] results = assertTimeoutPreemptively(
                Duration.ofSeconds(20),
                () -> geoCoder.geocode(KNOWN_PLACE));

        assertNotNull(results, "Result array should never be null");
        assertSuccessfulResultShapeIfPresent(results);
    }

    private static void assertSuccessfulResultShapeIfPresent(final GeocodingResult[] results) {
        if (results.length == 0) {
            return;
        }
        assertNotNull(results[0].formattedAddress,
                "Expected a formatted address for returned result");
        assertNotNull(results[0].geometry, "Expected geometry to be initialized");
        assertNotNull(results[0].geometry.location,
                "Expected latitude/longitude for returned result");
    }
}
