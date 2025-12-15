package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.geojson.LngLatAlt;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;

/**
 * @author Dick Schoeller
 */
public final class PlaceInfoTest {
    /** */
    @Test
    public void testNormalName() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals("name", pi.getPlaceName(), "input should match output");
    }

    /** */
    @Test
    public void testNormalLatitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals(Double.valueOf(1.0),
                Double.valueOf(pi.getLocation().getLatitude()),
                "input should match output");
    }

    /** */
    @Test
    public void testNormalLongitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals(Double.valueOf(2.0),
                Double.valueOf(pi.getLocation().getLongitude()),
                "input should match output");
    }

    /** */
    @Test
    public void testNormalToString() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals("{ \"placeName\":\"name\","
                + " \"latitude\":1.000000, \"longitude\":2.000000,"
                + " \"southwest\": {"
                + " \"latitude\":0.990000, \"longitude\":1.990000 },"
                + " \"northeast\": {"
                + " \"latitude\":1.010000, \"longitude\":2.010000 } }",
                pi.toString(), "input should match output");
    }

    /** */
    @Test
    public void testNormalNortheastLatitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals(Double.valueOf(1.0 + confidence),
                Double.valueOf(pi.getNortheast().getLatitude()), "mismatch");
    }

    /** */
    @Test
    public void testNormalNortheastLongitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals(Double.valueOf(2.0 + confidence),
                Double.valueOf(pi.getNortheast().getLongitude()), "mismatch");
    }

    /** */
    @Test
    public void testNormalSouthwestLatitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals(Double.valueOf(1.0 - confidence),
                Double.valueOf(pi.getSouthwest().getLatitude()), "mismatch");
    }

    /** */
    @Test
    public void testNormalSouthwestLongitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals(Double.valueOf(2.0 - confidence),
                Double.valueOf(pi.getSouthwest().getLongitude()), "mismatch");
    }

    /** */
    @Test
    public void testFullConstructorNortheastLatitude() {
        final double confidence = .02;
        final LngLatAlt location = new LngLatAlt(2.0, 1.0);
        final LngLatAlt northeast = new LngLatAlt(2.0 + confidence,
                1.0 + confidence);
        final LngLatAlt southwest = new LngLatAlt(2.0 - confidence,
                1.0 - confidence);
        final PlaceInfo pi = new PlaceInfo("name", location, southwest,
                northeast);
        assertEquals(Double.valueOf(1.0 + confidence),
                Double.valueOf(pi.getNortheast().getLatitude()), "mismatch");
    }

    /** */
    @Test
    public void testFullConstructorNortheastLongitude() {
        final double confidence = .02;
        final LngLatAlt location = new LngLatAlt(2.0, 1.0);
        final LngLatAlt northeast = new LngLatAlt(2.0 + confidence,
                1.0 + confidence);
        final LngLatAlt southwest = new LngLatAlt(2.0 - confidence,
                1.0 - confidence);
        final PlaceInfo pi = new PlaceInfo("name", location, southwest,
                northeast);
        assertEquals(Double.valueOf(2.0 + confidence),
                Double.valueOf(pi.getNortheast().getLongitude()), "mismatch");
    }

    /** */
    @Test
    public void testFullConstructorSouthwestLatitude() {
        final double confidence = .02;
        final LngLatAlt location = new LngLatAlt(2.0, 1.0);
        final LngLatAlt northeast = new LngLatAlt(2.0 + confidence,
                1.0 + confidence);
        final LngLatAlt southwest = new LngLatAlt(2.0 - confidence,
                1.0 - confidence);
        final PlaceInfo pi = new PlaceInfo("name", location, southwest,
                northeast);
        assertEquals(Double.valueOf(1.0 - confidence),
                Double.valueOf(pi.getSouthwest().getLatitude()), "mismatch");
    }

    /** */
    @Test
    public void testFullConstructorSouthwestLongitude() {
        final double confidence = .02;
        final LngLatAlt location = new LngLatAlt(2.0, 1.0);
        final LngLatAlt northeast = new LngLatAlt(2.0 + confidence,
                1.0 + confidence);
        final LngLatAlt southwest = new LngLatAlt(2.0 - confidence,
                1.0 - confidence);
        final PlaceInfo pi = new PlaceInfo("name", location, southwest,
                northeast);
        assertEquals(Double.valueOf(2.0 - confidence),
                Double.valueOf(pi.getSouthwest().getLongitude()), "mismatch");
    }

    /** */
    @Test
    public void testAbnormalName() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertNull(pi.getPlaceName(), "input should match output");
    }

    /** */
    @Test
    public void testAbnormalLatitude() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertTrue(Double.isNaN(pi.getLocation().getLatitude()),
                "null input ends up with not a number");
    }

    /** */
    @Test
    public void testAbnormalLongitude() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertTrue(Double.isNaN(pi.getLocation().getLongitude()),
                "null input ends up with not a number");
    }

    /** */
    @Test
    public void testAbormalToString() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertEquals("{ \"placeName\":null,"
                + " \"latitude\": NaN,"
                + " \"longitude\": NaN }",
                pi.toString(), "input should match output");
    }
}