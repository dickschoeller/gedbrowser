package org.schoellerfamily.geoservice.model.builder.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.geoservice.model.GeoServiceLatLng;

/**
 * @author Dick Schoeller
 */
public final class GeoServiceLatLngTest {
    /** */
    @Test
    public void testToString() {
        final GeoServiceLatLng bll = new GeoServiceLatLng(1.01, 2.02);
        assertEquals("Values don't match",
                "1.01000000,2.02000000", bll.toString());
    }

    /** */
    @Test
    public void testToUrlValue() {
        final GeoServiceLatLng bll = new GeoServiceLatLng(120.01, 170.02);
        assertEquals("Values don't match",
                "120.01000000,170.02000000", bll.toUrlValue());
    }
}
