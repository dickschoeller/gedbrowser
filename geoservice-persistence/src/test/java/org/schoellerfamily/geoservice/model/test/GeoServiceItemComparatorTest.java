package org.schoellerfamily.geoservice.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.model.GeoServiceBounds;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceGeometry;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.GeoServiceItemComparator;
import org.schoellerfamily.geoservice.model.builder.test.GeocodeValidator;

/**
 * @author Dick Schoeller
 */
class GeoServiceItemComparatorTest extends GeocodeValidator {
    /** */
    private GeoServiceGeocodingResult bgr;

    /** */
    @BeforeEach
    void setUp() {
        final double neLat = 10.00;
        final double neLng = 20.00;
        final LngLatAlt northeast = new LngLatAlt(neLng, neLat);
        final double swLat = 5.00;
        final double swLng = 25.00;
        final LngLatAlt southwest = new LngLatAlt(swLng, swLat);
        final Feature viewport = GeoServiceBounds.createBounds("viewport", southwest, northeast);
        final FeatureCollection geometry = GeoServiceGeometry.createFeatureCollection(null, null,
            null, viewport);
        bgr = new GeoServiceGeocodingResult(null, null, null, geometry, null, false, null);
    }

    /** */
    @Test
    void testCompareEqualExceptForName0() {
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("XYZZY", "ZZZZZ", bgr);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertTrue(c.compare(item0, item1) < 0, "Should be less than 0");
    }

    /** */
    @Test
    void testCompareEqualExceptForName2() {
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("ZZZZZ", "ZZZZZ", bgr);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertTrue(c.compare(item1, item0) > 0, "Should be greater than 0");
    }

    /** */
    @Test
    void testCompareEqualExceptForName3() {
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("ZZZZZ", "ZZZZZ", bgr);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertTrue(c.compare(item0, item1) < 0, "Should be less than 0");
    }

    /** */
    @Test
    void testCompareEqualExceptForName1() {
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("XYZZY", "ZZZZZ", bgr);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertTrue(c.compare(item1, item0) > 0, "Should be greater than 0");
    }

    /** */
    @Test
    void testCompareDifferentExceptForName() {
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("XYZZY", "PLUGH", null);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertEquals(0, c.compare(item0, item1), "Should be 0");
    }

    /** */
    @Test
    void testCompareDifferentExceptForModernName() {
        // Really only tracks "modern" name for sorting
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("ZZZZZ", "PLUGH", null);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertEquals(0, c.compare(item0, item1), "Should be 0");
    }
}
