package org.schoellerfamily.geoservice.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.geoservice.model.GeoServiceBounds;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceGeometry;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.GeoServiceItemComparator;
import org.schoellerfamily.geoservice.model.builder.test.GeocodeChecker;

/**
 * @author Dick Schoeller
 */
public class GeoServiceItemComparatorTest extends GeocodeChecker {
    /** */
    private GeoServiceGeocodingResult bgr;

    /** */
    @Before
    public void setUp() {
        final double neLat = 10.00;
        final double neLng = 20.00;
        final LngLatAlt northeast = new LngLatAlt(neLng, neLat);
        final double swLat = 5.00;
        final double swLng = 25.00;
        final LngLatAlt southwest = new LngLatAlt(swLng, swLat);
        final Feature viewport = GeoServiceBounds.createBounds("viewport",
                southwest, northeast);
        final FeatureCollection geometry = GeoServiceGeometry
                .createFeatureCollection(null, null, null, viewport);
        bgr = new GeoServiceGeocodingResult(
                null, null, null, geometry, null, false, null);
    }

    /** */
    @Test
    public void testCompareEqualExceptForName0() {
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("XYZZY", "ZZZZZ", bgr);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertTrue("Should be less than 0", c.compare(item0, item1) < 0);
    }

    /** */
    @Test
    public void testCompareEqualExceptForName2() {
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("ZZZZZ", "ZZZZZ", bgr);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertTrue("Should be greater than 0", c.compare(item1, item0) > 0);
    }

    /** */
    @Test
    public void testCompareEqualExceptForName3() {
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("ZZZZZ", "ZZZZZ", bgr);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertTrue("Should be less than 0", c.compare(item0, item1) < 0);
    }

    /** */
    @Test
    public void testCompareEqualExceptForName1() {
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("XYZZY", "ZZZZZ", bgr);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertTrue("Should be greater than 0", c.compare(item1, item0) > 0);
    }

    /** */
    @Test
    public void testCompareDifferentExceptForName() {
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("XYZZY", "PLUGH", null);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertEquals("Should be 0", 0, c.compare(item0, item1));
    }

    /** */
    @Test
    public void testCompareDifferentExceptForModernName() {
        // Really only tracks "modern" name for sorting
        final GeoServiceItem item0 = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoServiceItem item1 = new GeoServiceItem("ZZZZZ", "PLUGH", null);
        final GeoServiceItemComparator c = new GeoServiceItemComparator();
        assertEquals("Should be 0", 0, c.compare(item0, item1));
    }
}
