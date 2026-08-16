package org.schoellerfamily.geoservice.model.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;

/**
 * Contains tests for GeoServiceGeocodingResult.
 *
 * @author Richard Schoeller
 */
class GeoServiceGeocodingResultTest {
    /** */
    private static final int SINGLE_DIGIT_SEVEN = 7;
    /** */
    private static final int ANSWER_TO_EVERYTHING = 42;

    @Test
    void testPostcodeLocalitiesListConversion() {
        final GeoServiceGeocodingResult result =
            new GeoServiceGeocodingResult(null, null, null, geometryWithLocation(), null, false,
                null);
        result.getGeometry().getFeatures().get(0).setProperty("postcodeLocalities",
            List.of("foo", "bar"));

        assertArrayEquals(new String[] {"foo", "bar"}, result.getPropertyNamePostcodeLocalities(),
            "List should convert to String[]");
    }

    @Test
    void testPostcodeLocalitiesObjectArrayConversion() {
        final GeoServiceGeocodingResult result =
            new GeoServiceGeocodingResult(null, null, null, geometryWithLocation(), null, false,
                null);
        result.getGeometry().getFeatures().get(0).setProperty("postcodeLocalities",
            new Object[] {"foo", Integer.valueOf(SINGLE_DIGIT_SEVEN)});

        assertArrayEquals(new String[] {"foo", "7"}, result.getPropertyNamePostcodeLocalities(),
            "Object[] should convert to String[]");
    }

    @Test
    void testPostcodeLocalitiesScalarConversion() {
        final GeoServiceGeocodingResult result =
            new GeoServiceGeocodingResult(null, null, null, geometryWithLocation(), null, false,
                null);
        result.getGeometry().getFeatures().get(0).setProperty("postcodeLocalities",
            Integer.valueOf(ANSWER_TO_EVERYTHING));

        assertArrayEquals(new String[] {"42"}, result.getPropertyNamePostcodeLocalities(),
            "Scalar should be wrapped and converted to String[]");
    }

    private FeatureCollection geometryWithLocation() {
        final FeatureCollection geometry = new FeatureCollection();
        geometry.add(new Feature());
        return geometry;
    }
}
