package org.schoellerfamily.geoservice.persistence.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount" })
final class GeoCodeItemTest {
    /** */
    @Test
    void testEqualsSelf() {
        final GeoCodeItem gcce = new GeoCodeItem("temp");
        assertEquals(gcce, gcce, "Test of equals should match self");
    }

    /** */
    @Test
    void testNotEqualsNull() {
        final GeoCodeItem gcce = new GeoCodeItem("temp");
        assertNotEquals(gcce, null, "Test of equals should not match null");
    }

    /** */
    @Test
    void testEqualsPlaceName() {
        final GeoCodeItem gcce0 = new GeoCodeItem("temp");
        final GeoCodeItem gcce1 = new GeoCodeItem("temp");
        assertEquals(gcce0, gcce1, "Items constructed the same should match");
    }

    /** */
    @Test
    void testNotEqualsPlaceName() {
        final GeoCodeItem gcce0 = new GeoCodeItem("xyzzy");
        final GeoCodeItem gcce1 = new GeoCodeItem("plugh");
        assertNotEquals(gcce0, gcce1, "Items with different place names should not match");
    }

    /** */
    @Test
    void testEqualsPlaceAndNulls() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", (String) null);
        assertEquals(gcce0, gcce1, "Items constructed the same should match");
    }

    /** */
    @Test
    void testNotEqualsPlaceAndNullOne() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "foo");
        assertNotEquals(gcce0, gcce1, "Items with different modern name should not match");
    }

    /** */
    @Test
    void testNotEqualsPlaceAndNullTwo() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "foo");
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", (String) null);
        assertNotEquals(gcce0, gcce1);
    }

    /** */
    @Test
    void testEqualsPlaceAndModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp");
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp");
        assertEquals(gcce0, gcce1, "Items constructed the same should match");
    }

    /** */
    @Test
    void testNotEqualsPlaceNotModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "xyzzy");
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "plugh");
        assertNotEquals(gcce0, gcce1, "Items with different modern names should not match");
    }

    /** */
    @Test
    void testNotEqualsNotPlaceYesModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("xyzzy", "temp");
        final GeoCodeItem gcce1 = new GeoCodeItem("plugh", "temp");
        assertNotEquals(gcce0, gcce1, "Items with different names should not match");
    }

    /** */
    @Test
    void testNotEqualsNotPlaceNotModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("xyzzy", "foo");
        final GeoCodeItem gcce1 = new GeoCodeItem("plugh", "bar");
        assertNotEquals(gcce0, gcce1, "Items with different everything should not match");
    }

    /** */
    @Test
    void testEqualsPlaceModernAndNullResult() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", null);
        assertEquals(gcce0, gcce1, "Items constructed the same should match");
    }

    /** */
    @Test
    @SuppressWarnings("PMD.UnnecessaryFullyQualifiedName")
    void testNotEqualsClassMismatch() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp");
        // Necessary to be fully qualified to exercise the non-matching
        // data types.
        assertNotEquals(gcce0, "tamp", "Items of different types should not match");
    }

    /** */
    @Test
    void testNotEqualsNullPlaceName0() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp");
        assertNotEquals(gcce0, gcce1, "Items with name should not match empty (order 0)");
    }

    /** */
    @Test
    void testNotEqualsNullPlaceName1() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp");
        final GeoCodeItem gcce1 = new GeoCodeItem(null);
        assertNotEquals(gcce0, gcce1, "Items with name should not match empty (order 1)");
    }

    /** */
    @Test
    void testNotEqualsNullModernName0() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null, (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem(null, "tamp");
        assertNotEquals(gcce0, gcce1, "Items with modern should not match empty (order 0)");
    }

    /** */
    @Test
    void testNotEqualsNullModernName1() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null, "tamp");
        final GeoCodeItem gcce1 = new GeoCodeItem(null, (String) null);
        assertNotEquals(gcce0, gcce1, "Items with modern should not match empty (order 1)");
    }

    /** */
    @Test
    void testEqualsBothNullPlaceName() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null);
        final GeoCodeItem gcce1 = new GeoCodeItem(null);
        assertEquals(gcce0, gcce1, "Items constructed the same should match");
    }

    /** */
    @Test
    void testNotEqualsNullPlaceName0NullModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem(null, (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", (String) null);
        assertNotEquals(gcce0, gcce1, "Items with name should not match empty (order 0)");
    }

    /** */
    @Test
    void testNotEqualsNullPlaceName1NullModern() {
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", (String) null);
        final GeoCodeItem gcce1 = new GeoCodeItem(null, (String) null);
        assertNotEquals(gcce0, gcce1, "Items with name should not match empty (order 1)");
    }

    /** */
    @Test
    void testNotEqualsPlaceModernAndUnlikeResultString() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tampe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals(gcce0, gcce1, "Differences in the geo result should make not match");
    }

    /** */
    @Test
    void testNotEqualsPlaceModernAndNullResultString0() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = null;
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tampe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals(gcce0, gcce1, "Differences in the geo result should not match");
    }

    /** */
    @Test
    void testNotEqualsPlaceModernAndNullResultString1() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = null;
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals(gcce0, gcce1, "Differences in the geo result should not match");
    }

    /** */
    @Test
    void testEqualsPlaceModernAndNullContents() {
        final GeocodingResult gr0 = new GeocodingResult();
        final GeocodingResult gr1 = new GeocodingResult();
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertEquals(gcce0, gcce1, "Items built the same should match");
    }

    /** */
    @Test
    void testEqualsPlaceModernAndSameResult() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr);
        assertEquals(gcce0, gcce1, "Items built the same should match");
    }

    /** */
    @Test
    void testEqualsPlaceAndSameResult() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", gr);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", gr);
        assertEquals(gcce0, gcce1, "Items built the same should match");
    }

    /** */
    @Test
    void testNotEqualsPlaceModernAndNullResult0() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr);
        assertNotEquals(gcce0, gcce1, "Items built the same should match");
    }

    /** */
    @Test
    void testNotEqualsPlaceModernAndNullResult1() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", null);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr);
        assertNotEquals(gcce0, gcce1, "Null geocode result should not match non-null");
    }

    /** */
    @Test
    void testEqualsPlaceModernAndLikeResult() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertEquals(gcce0, gcce1, "Different but identical geo results should match");
    }

    /** */
    @Test
    void testEqualsByEmptyGeometry() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        gr1.geometry = new Geometry();
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertEquals(gcce0, gcce1, "Different but identical geo results should match");
    }

    /** */
    @Test
    void testEqualsByGeometry() {
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
        assertEquals(gcce0, gcce1, "Different but identical geo results should match");
    }

    /** */
    @Test
    void testNotEqualsNullGeometry0() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        gr1.geometry = new Geometry();
        gr1.geometry.location = new LatLng(1.0, 1.0);
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals(gcce0, gcce1, "Genuinely different geo results should not match");
    }

    /** */
    @Test
    void testNotEqualsNullGeometry1() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        gr0.geometry.location = new LatLng(1.0, 1.0);
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals(gcce0, gcce1, "Genuinely different geo results should not match");
    }

    /** */
    @Test
    void testNotEqualsEmptyGeometry0() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        gr1.geometry = new Geometry();
        gr1.geometry.location = new LatLng(1.0, 1.0);
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals(gcce0, gcce1, "Genuinely different geo results should not match");
    }

    /** */
    @Test
    void testNotEqualsEmptyGeometry1() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        gr0.geometry.location = new LatLng(1.0, 1.0);
        final GeocodingResult gr1 = new GeocodingResult();
        gr1.formattedAddress = "Tempe";
        gr1.geometry = new Geometry();
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        final GeoCodeItem gcce1 = new GeoCodeItem("tamp", "temp", gr1);
        assertNotEquals(gcce0, gcce1, "Genuinely different geo results should not match");
    }

    /** */
    @Test
    void testNotEqualsByLocation() {
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
        assertNotEquals(gcce0, gcce1, "Should have detected lat/lng difference");
    }

    /** */
    @Test
    void testGetPlaceName() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        assertEquals("tamp", gcce0.getPlaceName(), "Expected matching field value");
    }

    /** */
    @Test
    void testGetModernName() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        assertEquals("temp", gcce0.getModernPlaceName(), "Expected matching field value");
    }

    /** */
    @Test
    void testGetGeocodingResult() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce0 = new GeoCodeItem("tamp", "temp", gr0);
        assertEquals(gr0, gcce0.getGeocodingResult(), "Expected matching field value");
    }

    /** */
    @Test
    void testHashCodeNull() {
        final GeoCodeItem gcce = new GeoCodeItem(null);
        final int expected = 29791;
        assertEquals(expected, gcce.hashCode(), "Aren't hash codes special");
    }

    /** */
    @Test
    void testHashCodeName() {
        final GeoCodeItem gcce = new GeoCodeItem("name");
        final int expected = 107988415;
        assertEquals(expected, gcce.hashCode(), "And more stupid hash code tests");
    }

    /** */
    @Test
    void testHashCodeNameModern() {
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern");
        final int expected = 1230366635;
        assertEquals(expected, gcce.hashCode(), "One more twice");
    }

    /** */
    @Test
    void testHashCodeNameModernResult() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern", gr0);
        final int expected = 25917797;
        assertEquals(-expected, gcce.hashCode(), "Enough fields and wrap makes negative");
    }

    /** */
    @Test
    void testHashCodeNameModernResultGeometry() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern", gr0);
        final int expected = 25888006;
        assertEquals(-expected, gcce.hashCode(), "Another negative");
    }

    /** */
    @Test
    void testHashCodeNameModernResultLocation() {
        final GeocodingResult gr0 = new GeocodingResult();
        gr0.formattedAddress = "Tempe";
        gr0.geometry = new Geometry();
        gr0.geometry.location = new LatLng(1.0, 1.0);
        final GeoCodeItem gcce = new GeoCodeItem("name", "modern", gr0);
        final int expected = 1082540794;
        assertEquals(-expected, gcce.hashCode(), "Last hash code");
    }
}
