package org.schoellerfamily.gedbrowser.geocode.dao.test;

import org.junit.Assert;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.geocode.dao.GeoCodeItem;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods",
    "PMD.ExcessivePublicCount" })
public final class GeoCodeItemTest {
    /** */
    @Test
    public void testEqualsSelf() {
        final GeoCodeItem gcce = new GeoCodeItem("temp");
        Assert.assertEquals(gcce, gcce);
    }

    /** */
    @Test
    public void testNotEqualsNull() {
        final GeoCodeItem gcce = new GeoCodeItem("temp");
        Assert.assertNotEquals(gcce, null);
    }

    /** */
    @Test
    public void testEqualsPlaceName() {
        final GeoCodeItem gcce0 = new GeoCodeItem("temp");
        final GeoCodeItem gcce1 = new GeoCodeItem("temp");
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceName() {
        final GeoCodeItem gcce0 = new GeoCodeItem("xyzzy");
        final GeoCodeItem gcce1 = new GeoCodeItem("plugh");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceAndNulls() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp",
                (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp",
                (String) null);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceAndNullOne() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp",
                (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "foo");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceAndNullTwo() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "foo");
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp",
                (String) null);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceAndModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp");
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp");
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceNotModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "xyzzy");
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "plugh");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNotPlaceYesModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("xyzzy", "temp");
        final GeoCodeItem gcce1 = new GeoCodeItem("plugh", "temp");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNotPlaceNotModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("xyzzy", "foo");
        final GeoCodeItem gcce1 = new GeoCodeItem("plugh", "bar");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceModernAndNullResult() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
                null);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsClassMismatch() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp");
        Assert.assertNotEquals(gcce0, "tamp");
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName0() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName1() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp");
        final GeoCodeItem gcce1 = new GeoCodeItem(null);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullModernName0() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null,
                (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem(null, "tamp");
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullModernName1() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null, "tamp");
        final GeoCodeItem gcce1 = new GeoCodeItem(null,
                (String) null);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsBothNullPlaceName() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null);
        final GeoCodeItem gcce1 = new GeoCodeItem(null);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName0NullModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null,
                (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp",
                (String) null);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName1NullModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp",
                (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem(null,
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
                gr1);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceModernAndNullContents() {
        final GeocodingResult gr0 = new GeocodingResult();
        final GeocodingResult gr1 = new GeocodingResult();
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
                gr1);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceModernAndSameResult() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
                gr);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceAndSameResult() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", gr);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", gr);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndNullResult0() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
                gr);
        Assert.assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndNullResult1() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
                gr1);
        Assert.assertNotEquals(gcce0, gcce1);
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
                gr1);
        Assert.assertNotEquals(gcce0, gcce1);
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
                gr1);
        Assert.assertNotEquals(gcce0, gcce1);
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
                gr1);
        Assert.assertNotEquals(gcce0, gcce1);
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp",
                gr1);
        Assert.assertEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testGetPlaceName() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        Assert.assertEquals("tamp", gcce0.getPlaceName());
    }

    /** */
    @Test
    public void testGetModernName() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        Assert.assertEquals("temp", gcce0.getModernPlaceName());
    }

    /** */
    @Test
    public void testGetGeocodingResult() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp",
                gr0);
        Assert.assertEquals(gr0, gcce0.getGeocodingResult());
    }

    /** */
    @Test
    public void testHashCodeNull() {
        final GeoCodeItem gcce = new GeoCodeItem(null);
        final int expected = 29791;
        Assert.assertEquals(expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeName() {
        final GeoCodeItem gcce = new GeoCodeItem("name");
        final int expected = 107988415;
        Assert.assertEquals(expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeNameModern() {
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern");
        final int expected = 1230366635;
        Assert.assertEquals(expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeNameModernResult() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern",
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
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern",
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
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern",
                gr0);
        final int expected = 1082540794;
        Assert.assertEquals(-expected, gcce.hashCode());
    }
}
