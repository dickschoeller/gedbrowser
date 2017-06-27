package org.schoellerfamily.geoservice.model.builder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.builder.GeocodeResultBuilder;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.AddressType;
import com.google.maps.model.Bounds;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import com.google.maps.model.LocationType;

/**
 * @author Dick Schoeller
 */
public class GeocodeResultBuilderToBackupTest extends GeocodeChecker {
    /** */
    private final GeocodeResultBuilder builder = new GeocodeResultBuilder();


    /** */
    @Test
    public void testToBackupGeoCodeItemNull() {
        assertNull("Null gets null", builder.toGeoServiceItem(null));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemName() {
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", null);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertEquals("Mismatched name", "XYZZY", bgci.getPlaceName());
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemModernName() {
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", null);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertEquals("Mismatched modern name", "PLUGH",
                bgci.getModernPlaceName());
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemNullResult() {
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", null);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemEmptyResult() {
        final GeocodingResult gr = new GeocodingResult();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultAddressComponent() {
        final GeocodingResult gr = new GeocodingResult();
        gr.addressComponents = new AddressComponent[1];
        gr.addressComponents[0] = new AddressComponent();
        gr.addressComponents[0].longName = "Foo Bar";
        gr.addressComponents[0].shortName = "Foo";
        gr.addressComponents[0].types = new AddressComponentType[1];
        gr.addressComponents[0].types[0] =
                AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1;
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultEmptyAddressComponent() {
        final GeocodingResult gr = new GeocodingResult();
        gr.addressComponents = new AddressComponent[1];
        gr.addressComponents[0] = new AddressComponent();
        gr.addressComponents[0].longName = "Foo Bar";
        gr.addressComponents[0].shortName = "Foo";
        gr.addressComponents[0].types = new AddressComponentType[0];
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultAddress() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "formatted address";
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultPlaceId() {
        final GeocodingResult gr = new GeocodingResult();
        gr.placeId = "kfdhasfokjhkljdasf";
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultPostcodeLocality() {
        final GeocodingResult gr = new GeocodingResult();
        gr.postcodeLocalities = new String[1];
        gr.postcodeLocalities[0] = "foobar";
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultAddressType() {
        final GeocodingResult gr = new GeocodingResult();
        gr.types = new AddressType[1];
        gr.types[0] = AddressType.ADMINISTRATIVE_AREA_LEVEL_1;
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultEmptyGeometry() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryLocation() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        final double lat = 10.00;
        final double lng = 20.00;
        gr.geometry.location = new LatLng(lat, lng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryEmptyBounds() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.bounds = new Bounds();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        try {
            final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
            fail("should have thrown an illegal argument exception: " + bgci);
        } catch (IllegalArgumentException e) {
            // Expect to throw.
        }
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryBoundsWithNE() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.bounds = new Bounds();
        final double lat = 10.00;
        final double lng = 20.00;
        gr.geometry.bounds.northeast = new LatLng(lat, lng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        try {
            final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
            fail("Should have thrown illegal argument exception: " + bgci);
        } catch (IllegalArgumentException e) {
            // Should have thrown
        }

    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryBoundsWithSW() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.bounds = new Bounds();
        final double lat = 10.00;
        final double lng = 20.00;
        gr.geometry.bounds.southwest = new LatLng(lat, lng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        try {
            final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
            fail("Should have thrown illegal argument exception: " + bgci);
        } catch (IllegalArgumentException e) {
            // Should have thrown
        }
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryBoundsWithBoth() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.bounds = new Bounds();
        final double neLat = 10.00;
        final double neLng = 20.00;
        gr.geometry.bounds.northeast = new LatLng(neLat, neLng);
        final double swLat = 5.00;
        final double swLng = 25.00;
        gr.geometry.bounds.southwest = new LatLng(swLat, swLng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryLocationType() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.locationType = LocationType.APPROXIMATE;
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryEmptyViewport() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.viewport = new Bounds();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        try {
            final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
            fail("should have thrown an illegal argument exception" + bgci);
        } catch (IllegalArgumentException e) {
            // Expect to throw.
        }
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryViewportNE() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.viewport = new Bounds();
        final double lat = 10.00;
        final double lng = 20.00;
        gr.geometry.viewport.northeast = new LatLng(lat, lng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        try {
            final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
            fail("should have thrown an illegal argument exception" + bgci);
        } catch (IllegalArgumentException e) {
            // Expect to throw.
        }

    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryViewportSW() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.viewport = new Bounds();
        final double lat = 5.00;
        final double lng = 25.00;
        gr.geometry.viewport.southwest = new LatLng(lat, lng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        try {
            final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
            fail("should have thrown an illegal argument exception" + bgci);
        } catch (IllegalArgumentException e) {
            // Expect to throw.
        }
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryViewportBoth() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.viewport = new Bounds();
        final double neLat = 10.00;
        final double neLng = 20.00;
        gr.geometry.viewport.northeast = new LatLng(neLat, neLng);
        final double swLat = 5.00;
        final double swLng = 25.00;
        gr.geometry.viewport.southwest = new LatLng(swLat, swLng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }
}
