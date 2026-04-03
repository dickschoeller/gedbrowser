package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.geojson.LngLatAlt;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;



/**
 * Contains tests for place info.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
final class PlaceInfoTest {
    @Test
    void testNormalName() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals("name", pi.getPlaceName(), "input should match output");
    }

    @Test
    void testNormalLatitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals(Double.valueOf(1.0), Double.valueOf(pi.getLocation().getLatitude()),
            "input should match output");
    }

    @Test
    void testNormalLongitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals(Double.valueOf(2.0), Double.valueOf(pi.getLocation().getLongitude()),
            "input should match output");
    }

    @Test
    void testNormalToString() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals(
            "{ \"placeName\":\"name\"," + " \"latitude\":1.000000, \"longitude\":2.000000,"
                + " \"southwest\": {" + " \"latitude\":0.990000, \"longitude\":1.990000 },"
                + " \"northeast\": {" + " \"latitude\":1.010000, \"longitude\":2.010000 } }",
            pi.toString(), "input should match output");
    }

    @Test
    void testNormalNortheastLatitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals(Double.valueOf(1.0 + confidence),
            Double.valueOf(pi.getNortheast().getLatitude()), "mismatch");
    }

    @Test
    void testNormalNortheastLongitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals(Double.valueOf(2.0 + confidence),
            Double.valueOf(pi.getNortheast().getLongitude()), "mismatch");
    }

    @Test
    void testNormalSouthwestLatitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals(Double.valueOf(1.0 - confidence),
            Double.valueOf(pi.getSouthwest().getLatitude()), "mismatch");
    }

    @Test
    void testNormalSouthwestLongitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals(Double.valueOf(2.0 - confidence),
            Double.valueOf(pi.getSouthwest().getLongitude()), "mismatch");
    }

    @Test
    void testFullConstructorNortheastLatitude() {
        final double confidence = .02;
        final LngLatAlt location = new LngLatAlt(2.0, 1.0);
        final LngLatAlt northeast = new LngLatAlt(2.0 + confidence, 1.0 + confidence);
        final LngLatAlt southwest = new LngLatAlt(2.0 - confidence, 1.0 - confidence);
        final PlaceInfo pi = new PlaceInfo("name", location, southwest, northeast);
        assertEquals(Double.valueOf(1.0 + confidence),
            Double.valueOf(pi.getNortheast().getLatitude()), "mismatch");
    }

    @Test
    void testFullConstructorNortheastLongitude() {
        final double confidence = .02;
        final LngLatAlt location = new LngLatAlt(2.0, 1.0);
        final LngLatAlt northeast = new LngLatAlt(2.0 + confidence, 1.0 + confidence);
        final LngLatAlt southwest = new LngLatAlt(2.0 - confidence, 1.0 - confidence);
        final PlaceInfo pi = new PlaceInfo("name", location, southwest, northeast);
        assertEquals(Double.valueOf(2.0 + confidence),
            Double.valueOf(pi.getNortheast().getLongitude()), "mismatch");
    }

    @Test
    void testFullConstructorSouthwestLatitude() {
        final double confidence = .02;
        final LngLatAlt location = new LngLatAlt(2.0, 1.0);
        final LngLatAlt northeast = new LngLatAlt(2.0 + confidence, 1.0 + confidence);
        final LngLatAlt southwest = new LngLatAlt(2.0 - confidence, 1.0 - confidence);
        final PlaceInfo pi = new PlaceInfo("name", location, southwest, northeast);
        assertEquals(Double.valueOf(1.0 - confidence),
            Double.valueOf(pi.getSouthwest().getLatitude()), "mismatch");
    }

    @Test
    void testFullConstructorSouthwestLongitude() {
        final double confidence = .02;
        final LngLatAlt location = new LngLatAlt(2.0, 1.0);
        final LngLatAlt northeast = new LngLatAlt(2.0 + confidence, 1.0 + confidence);
        final LngLatAlt southwest = new LngLatAlt(2.0 - confidence, 1.0 - confidence);
        final PlaceInfo pi = new PlaceInfo("name", location, southwest, northeast);
        assertEquals(Double.valueOf(2.0 - confidence),
            Double.valueOf(pi.getSouthwest().getLongitude()), "mismatch");
    }

    @Test
    void testAbnormalName() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertNull(pi.getPlaceName(), "input should match output");
    }

    @Test
    void testAbnormalLatitude() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertTrue(Double.isNaN(pi.getLocation().getLatitude()),
            "null input ends up with not a number");
    }

    @Test
    void testAbnormalLongitude() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertTrue(Double.isNaN(pi.getLocation().getLongitude()),
            "null input ends up with not a number");
    }

    @Test
    void testAbormalToString() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertEquals("{ \"placeName\":null," + " \"latitude\": NaN," + " \"longitude\": NaN }",
            pi.toString(), "input should match output");
    }

    @Test
    @SuppressWarnings("checkstyle:MagicNumber")
    void testObjectConstructorSupportsMapAndStringCoordinates() {
        final PlaceInfo pi = new PlaceInfo(
            "name",
            Map.of("lng", "2.5", "lat", "1.5"),
            Map.of("coordinates", List.of(2.4, 1.4)),
            Map.of("coordinates", new Object[] {2.6, 1.6}));

        assertEquals(1.5, pi.getLocation().getLatitude());
        assertEquals(2.5, pi.getLocation().getLongitude());
        assertEquals(1.4, pi.getSouthwest().getLatitude());
        assertEquals(2.4, pi.getSouthwest().getLongitude());
        assertEquals(1.6, pi.getNortheast().getLatitude());
        assertEquals(2.6, pi.getNortheast().getLongitude());
    }

    @Test
    @SuppressWarnings("checkstyle:MagicNumber")
    void testObjectConstructorSupportsListCoordinates() {
        final PlaceInfo pi = new PlaceInfo(
            "name",
            List.of(2.0, 1.0),
            List.of(1.9, 0.9),
            List.of(2.1, 1.1));

        assertEquals(1.0, pi.getLocation().getLatitude());
        assertEquals(2.0, pi.getLocation().getLongitude());
        assertEquals(0.9, pi.getSouthwest().getLatitude());
        assertEquals(1.9, pi.getSouthwest().getLongitude());
        assertEquals(1.1, pi.getNortheast().getLatitude());
        assertEquals(2.1, pi.getNortheast().getLongitude());
    }

    @Test
    @SuppressWarnings("checkstyle:MagicNumber")
    void testObjectConstructorSupportsObjectArrayCoordinates() {
        final PlaceInfo pi = new PlaceInfo(
            "name",
            new Object[] {2.0, 1.0},
            new Object[] {1.9, 0.9},
            new Object[] {2.1, 1.1});

        assertEquals(1.0, pi.getLocation().getLatitude());
        assertEquals(2.0, pi.getLocation().getLongitude());
        assertEquals(0.9, pi.getSouthwest().getLatitude());
        assertEquals(1.9, pi.getSouthwest().getLongitude());
        assertEquals(1.1, pi.getNortheast().getLatitude());
        assertEquals(2.1, pi.getNortheast().getLongitude());
    }

    @Test
    void testObjectConstructorHandlesShortOrUnsupportedCoordinates() {
        final PlaceInfo shortCoords = new PlaceInfo(
            "name",
            Map.of("coordinates", List.of(2.0)),
            Map.of("coordinates", "unsupported"),
            null);

        assertTrue(Double.isNaN(shortCoords.getLocation().getLatitude()));
        assertTrue(Double.isNaN(shortCoords.getLocation().getLongitude()));
        assertTrue(Double.isNaN(shortCoords.getSouthwest().getLatitude()));
        assertTrue(Double.isNaN(shortCoords.getSouthwest().getLongitude()));
        assertTrue(Double.isNaN(shortCoords.getNortheast().getLatitude()));
        assertTrue(Double.isNaN(shortCoords.getNortheast().getLongitude()));
    }

    @Test
    void testObjectConstructorHandlesInvalidMapNumericValues() {
        final PlaceInfo badValues = new PlaceInfo(
            "name",
            Map.of("lng", "x", "lat", true),
            null,
            null);

        assertTrue(Double.isNaN(badValues.getLocation().getLatitude()));
        assertTrue(Double.isNaN(badValues.getLocation().getLongitude()));
    }

    @Test
    void testObjectConstructorUsesNaNSentinelsForUnsupportedPayloads() {
        final PlaceInfo pi =
            new PlaceInfo("name", "bad", Map.of("coordinates", List.of("x")), null);

        assertTrue(Double.isNaN(pi.getLocation().getLatitude()));
        assertTrue(Double.isNaN(pi.getLocation().getLongitude()));
        assertTrue(Double.isNaN(pi.getSouthwest().getLatitude()));
        assertTrue(Double.isNaN(pi.getSouthwest().getLongitude()));
        assertTrue(Double.isNaN(pi.getNortheast().getLatitude()));
        assertTrue(Double.isNaN(pi.getNortheast().getLongitude()));
    }
}
