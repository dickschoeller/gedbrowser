package org.schoellerfamily.geoservice.persistence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Assert;
import org.junit.Test;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount" })
public final class GeoCodeItemTest {
    /** */
    @Test
    public void testEqualsSelf() {
        final GeoCodeItem gcce = new GeoCodeItem("temp");
        assertEquals("Test of equals should match self", gcce, gcce);
    }

    /** */
    @Test
    public void testNotEqualsNull() {
        final GeoCodeItem gcce = new GeoCodeItem("temp");
        assertNotEquals("Test of equals should not match null", gcce, null);
    }

    /** */
    @Test
    public void testEqualsPlaceName() {
        final GeoCodeItem gcce0 = new GeoCodeItem("temp");
        final GeoCodeItem gcce1 = new GeoCodeItem("temp");
        assertEquals("Items constructed the same should match", gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceName() {
        final GeoCodeItem gcce0 = new GeoCodeItem("xyzzy");
        final GeoCodeItem gcce1 = new GeoCodeItem("plugh");
        assertNotEquals("Items with different place names should not match",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceAndNulls() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", (String) null);
        assertEquals("Items constructed the same should match", gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceAndNullOne() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "foo");
        assertNotEquals("Items with different modern name should not match",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceAndNullTwo() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "foo");
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", (String) null);
        assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceAndModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp");
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp");
        assertEquals("Items constructed the same should match", gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceNotModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "xyzzy");
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "plugh");
        assertNotEquals("Items with different modern names should not match",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNotPlaceYesModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("xyzzy", "temp");
        final GeoCodeItem gcce1 = new GeoCodeItem("plugh", "temp");
        assertNotEquals("Items with different names should not match",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNotPlaceNotModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("xyzzy", "foo");
        final GeoCodeItem gcce1 = new GeoCodeItem("plugh", "bar");
        assertNotEquals("Items with different everything should not match",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceModernAndNullResult() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", null);
        assertEquals("Items constructed the same should match", gcce0, gcce1);
    }

    /** */
    @Test
    @SuppressWarnings("PMD.UnnecessaryFullyQualifiedName")
    public void testNotEqualsClassMismatch() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp");
        // Necessary to be fully qualified to exercise the non-matching
        // data types.
        Assert.assertNotEquals("Items of different types should not match",
                gcce0, "tamp");
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName0() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp");
        assertNotEquals("Items with name should not match empty (order 0)",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName1() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp");
        final GeoCodeItem gcce1 = new GeoCodeItem(null);
        assertNotEquals("Items with name should not match empty (order 1)",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullModernName0() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null, (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem(null, "tamp");
        assertNotEquals("Items with modern should not match empty (order 0)",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullModernName1() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null, "tamp");
        final GeoCodeItem gcce1 = new GeoCodeItem(null, (String) null);
        assertNotEquals("Items with modern should not match empty (order 1)",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsBothNullPlaceName() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null);
        final GeoCodeItem gcce1 = new GeoCodeItem(null);
        assertEquals("Items constructed the same should match", gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName0NullModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null, (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", (String) null);
        assertNotEquals("Items with name should not match empty (order 0)",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsNullPlaceName1NullModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem(null, (String) null);
        assertNotEquals("Items with name should not match empty (order 1)",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndUnlikeResultString() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tampe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals("Differences in the geo result should make not match",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndNullResultString0() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = null;
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tampe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals("Differences in the geo result should not match",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndNullResultString1() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = null;
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals("Differences in the geo result should not match",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceModernAndNullContents() {
        final GeocodingResult gr0 = new GeocodingResult();
        final GeocodingResult gr1 = new GeocodingResult();
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertEquals("Items built the same should match", gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceModernAndSameResult() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr);
        assertEquals("Items built the same should match", gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceAndSameResult() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", gr);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", gr);
        assertEquals("Items built the same should match", gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndNullResult0() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr);
        assertNotEquals("Items built the same should match", gcce0, gcce1);
    }

    /** */
    @Test
    public void testNotEqualsPlaceModernAndNullResult1() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr);
        assertNotEquals("Null geocode result should not match non-null",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testEqualsPlaceModernAndLikeResult() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertEquals("Different but identical geo results should match",
                gcce0, gcce1);
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertEquals("Different but identical geo results should match",
                gcce0, gcce1);
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertEquals("Different but identical geo results should match",
                gcce0, gcce1);
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals("Genuinely different geo results should not match",
                gcce0, gcce1);
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals("Genuinely different geo results should not match",
                gcce0, gcce1);
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals("Genuinely different geo results should not match",
                gcce0, gcce1);
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals("Genuinely different geo results should not match",
                gcce0, gcce1);
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
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals("Should have detected lat/lng difference",
                gcce0, gcce1);
    }

    /** */
    @Test
    public void testGetPlaceName() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        assertEquals("Expected matching field value",
                "tamp", gcce0.getPlaceName());
    }

    /** */
    @Test
    public void testGetModernName() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        assertEquals("Expected matching field value",
                "temp", gcce0.getModernPlaceName());
    }

    /** */
    @Test
    public void testGetGeocodingResult() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        assertEquals("Expected matching field value",
                gr0, gcce0.getGeocodingResult());
    }

    /** */
    @Test
    public void testHashCodeNull() {
        final GeoCodeItem gcce = new GeoCodeItem(null);
        final int expected = 29791;
        assertEquals("Aren't hash codes special",
                expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeName() {
        final GeoCodeItem gcce = new GeoCodeItem("name");
        final int expected = 107988415;
        assertEquals("And more stupid hash code tests",
                expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeNameModern() {
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern");
        final int expected = 1230366635;
        assertEquals("One more twice",
                expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeNameModernResult() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern", gr0);
        final int expected = 25917797;
        assertEquals("Enough fields and wrap makes negative",
                -expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeNameModernResultGeometry() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern", gr0);
        final int expected = 25888006;
        assertEquals("Another negative", -expected, gcce.hashCode());
    }

    /** */
    @Test
    public void testHashCodeNameModernResultLocation() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        gr0.geometry.location = new LatLng(1.0, 1.0);
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern", gr0);
        final int expected = 1082540794;
        assertEquals("Last hash code", -expected, gcce.hashCode());
    }
}
