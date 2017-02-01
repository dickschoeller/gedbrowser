package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.geojson.LngLatAlt;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;

/**
 * @author Dick Schoeller
 */
public final class PlaceInfoTest {
    /** */
    @Test
    public void testNormalName() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals("input should match output", "name", pi.getPlaceName());
    }

    /** */
    @Test
    public void testNormalLatitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals("input should match output", Double.valueOf(1.0),
                Double.valueOf(pi.getLocation().getLatitude()));
    }

    /** */
    @Test
    public void testNormalLongitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals("input should match output", Double.valueOf(2.0),
                Double.valueOf(pi.getLocation().getLongitude()));
    }

    /** */
    @Test
    public void testNormalToString() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals("input should match output",
                "{ \"placeName\":\"name\","
                + " \"latitude\":1.000000, \"longitude\":2.000000,"
                + " \"southwest\": {"
                + " \"latitude\":0.990000, \"longitude\":1.990000 },"
                + " \"northeast\": {"
                + " \"latitude\":1.010000, \"longitude\":2.010000 } }",
                pi.toString());
    }

    /** */
    @Test
    public void testNormalNortheastLatitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals("mismatch", Double.valueOf(1.0 + confidence),
                Double.valueOf(pi.getNortheast().getLatitude()));
    }

    /** */
    @Test
    public void testNormalNortheastLongitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals("mismatch", Double.valueOf(2.0 + confidence),
                Double.valueOf(pi.getNortheast().getLongitude()));
    }

    /** */
    @Test
    public void testNormalSouthwestLatitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals("mismatch", Double.valueOf(1.0 - confidence),
                Double.valueOf(pi.getSouthwest().getLatitude()));
    }

    /** */
    @Test
    public void testNormalSouthwestLongitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        final double confidence = .01;
        assertEquals("mismatch", Double.valueOf(2.0 - confidence),
                Double.valueOf(pi.getSouthwest().getLongitude()));
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
        assertEquals("mismatch", Double.valueOf(1.0 + confidence),
                Double.valueOf(pi.getNortheast().getLatitude()));
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
        assertEquals("mismatch", Double.valueOf(2.0 + confidence),
                Double.valueOf(pi.getNortheast().getLongitude()));
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
        assertEquals("mismatch", Double.valueOf(1.0 - confidence),
                Double.valueOf(pi.getSouthwest().getLatitude()));
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
        assertEquals("mismatch", Double.valueOf(2.0 - confidence),
                Double.valueOf(pi.getSouthwest().getLongitude()));
    }

    /** */
    @Test
    public void testAbnormalName() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertNull("input should match output", pi.getPlaceName());
    }

    /** */
    @Test
    public void testAbnormalLatitude() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertTrue("null input ends up with not a number",
                Double.isNaN(pi.getLocation().getLatitude()));
    }

    /** */
    @Test
    public void testAbnormalLongitude() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertTrue("null input ends up with not a number",
                Double.isNaN(pi.getLocation().getLongitude()));
    }

    /** */
    @Test
    public void testAbormalToString() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertEquals("input should match output", "{ \"placeName\":null,"
                + " \"latitude\": NaN,"
                + " \"longitude\": NaN }",
                pi.toString());
    }
}
