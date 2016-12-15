package org.schoellerfamily.gedbrowser.geocodecache.test;

import org.junit.Assert;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.geocodecache.GeoCodeCacheEntry;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods",
    "PMD.ExcessivePublicCount" })
public class GeoCodeCacheEntryTest {
    /** */
    @Test
    public void testEqualsSelf() {
        final GeoCodeCacheEntry gcce = new GeoCodeCacheEntry("temp");
        Assert.assertEquals(gcce, gcce);
    }

    /** */
    @Test
    public void testNotEqualsNull() {
        final GeoCodeCacheEntry gcce = new GeoCodeCacheEntry("temp");
        Assert.assertNotEquals(gcce, null);
    }

    /** */
    @Test
    public void testEqualsPlaceName() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("temp");
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("temp");
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceName() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("xyzzy");
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("plugh");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceAndNulls() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp",
                (String) null);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp",
                (String) null);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceAndNullOne() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp",
                (String) null);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "foo");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceAndNullTwo() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "foo");
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp",
                (String) null);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceAndModern() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp");
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp");
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceNotModern() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "xyzzy");
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "plugh");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNotPlaceYesModern() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("xyzzy", "temp");
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("plugh", "temp");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNotPlaceNotModern() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("xyzzy", "foo");
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("plugh", "bar");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceModernAndNullResult() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                null);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                null);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsClassMismatch() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp");
        Assert.assertNotEquals(gcce0, "tamp");
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName0() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry(null);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName1() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp");
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry(null);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullModernName0() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry(null,
                (String) null);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry(null, "tamp");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullModernName1() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry(null, "tamp");
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry(null,
                (String) null);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsBothNullPlaceName() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry(null);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry(null);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName0NullModern() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry(null,
                (String) null);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp",
                (String) null);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName1NullModern() {
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp",
                (String) null);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry(null,
                (String) null);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndUnlikeResultString() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tampe";
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndNullResultString0() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = null;
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tampe";
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndNullResultString1() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = null;
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceModernAndNullContents() {
        final GeocodingResult gr0 = new GeocodingResult();
        final GeocodingResult gr1 = new GeocodingResult();
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceModernAndSameResult() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceAndSameResult() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", gr);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", gr);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndNullResult0() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                null);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndNullResult1() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                null);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceModernAndLikeResult() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsByEmptyGeometry() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        gr1.geometry = new Geometry();
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsByGeometry() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        gr0.geometry.location = new LatLng(1.0, 1.0);
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        gr1.geometry = new Geometry();
        gr1.geometry.location = new LatLng(1.0, 1.0);
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullGeometry0() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        gr1.geometry = new Geometry();
        gr1.geometry.location = new LatLng(1.0, 1.0);
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullGeometry1() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        gr0.geometry.location = new LatLng(1.0, 1.0);
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsEmptyGeometry0() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        gr1.geometry = new Geometry();
        gr1.geometry.location = new LatLng(1.0, 1.0);
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsEmptyGeometry1() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        gr0.geometry.location = new LatLng(1.0, 1.0);
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        gr1.geometry = new Geometry();
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsByLocation() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        gr0.geometry.location = new LatLng(0.0, 0.0);
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        gr1.geometry = new Geometry();
        gr1.geometry.location = new LatLng(1.0, 1.0);
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        final GeoCodeCacheEntry gcce1 = new GeoCodeCacheEntry("tamp", "temp",
                gr1);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testGetPlaceName() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        Assert.assertEquals("tamp", gcce0.getPlaceName());
    }

    /** */
    @Test
    public void testGetModernName() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        Assert.assertEquals("temp", gcce0.getModernPlaceName());
    }

    /** */
    @Test
    public void testGetGeocodingResult() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeCacheEntry gcce0 = new GeoCodeCacheEntry("tamp", "temp",
                gr0);
        Assert.assertEquals(gr0, gcce0.getGeocodingResult());
    }

    /** */
    @Test
    public void testHashCodeNull() {
        final GeoCodeCacheEntry gcce = new GeoCodeCacheEntry(null);
        final int expected = 29791;
        Assert.assertEquals(expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeName() {
        final GeoCodeCacheEntry gcce = new GeoCodeCacheEntry("name");
        final int expected = 107988415;
        Assert.assertEquals(expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeNameModern() {
        final GeoCodeCacheEntry gcce = new GeoCodeCacheEntry("name", "modern");
        final int expected = 1230366635;
        Assert.assertEquals(expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeNameModernResult() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeCacheEntry gcce = new GeoCodeCacheEntry("name", "modern",
                gr0);
        final int expected = 25917797;
        Assert.assertEquals(-expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeNameModernResultGeometry() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        final GeoCodeCacheEntry gcce = new GeoCodeCacheEntry("name", "modern",
                gr0);
        final int expected = 25888006;
        Assert.assertEquals(-expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeNameModernResultLocation() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        gr0.geometry.location = new LatLng(1.0, 1.0);
        final GeoCodeCacheEntry gcce = new GeoCodeCacheEntry("name", "modern",
                gr0);
        final int expected = 1082540794;
        Assert.assertEquals(-expected, gcce.hashCode());
    }
}
