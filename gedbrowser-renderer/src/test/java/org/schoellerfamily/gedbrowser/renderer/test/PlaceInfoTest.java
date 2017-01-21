package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;

/**
 * @author Dick Schoeller
 */
public class PlaceInfoTest {
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
                pi.getLatitude());
    }

    /** */
    @Test
    public void testNormalLongitude() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals("input should match output", Double.valueOf(2.0),
                pi.getLongitude());
    }

    /** */
    @Test
    public void testNormalToString() {
        final PlaceInfo pi = new PlaceInfo("name", 1.0, 2.0);
        assertEquals("input should match output",
                "{ \"placeName\":\"name\","
                + " \"latitude\":1.000000,"
                + " \"longitude\":2.000000 }",
                pi.toString());
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
                pi.getLatitude().isNaN());
    }

    /** */
    @Test
    public void testAbnormalLongitude() {
        final PlaceInfo pi = new PlaceInfo(null, null, null);
        assertTrue("null input ends up with not a number",
                pi.getLongitude().isNaN());
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
