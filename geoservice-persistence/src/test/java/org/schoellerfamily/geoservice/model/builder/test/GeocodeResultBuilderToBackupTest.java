package org.schoellerfamily.geoservice.model.builder.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
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
 * Contains tests for geocode result builder to backup.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods" })
class GeocodeResultBuilderToBackupTest extends GeocodeValidator {
    private final GeocodeResultBuilder builder = new GeocodeResultBuilder();

    @Test
    void testToBackupGeoCodeItemNull() {
        assertNull(builder.toGeoServiceItem(null), "Null gets null");
    }

    @Test
    void testToBackupGeoCodeItemName() {
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", null);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertEquals("XYZZY", bgci.getPlaceName(), "Mismatched name");
    }

    @Test
    void testToBackupGeoCodeItemModernName() {
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", null);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertEquals("PLUGH", bgci.getModernPlaceName(), "Mismatched modern name");
    }

    @Test
    void testToBackupGeoCodeItemNullResult() {
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", null);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemEmptyResult() {
        final GeocodingResult gr = new GeocodingResult();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemResultAddressComponent() {
        final GeocodingResult gr = new GeocodingResult();
        gr.addressComponents = new AddressComponent[1];
        gr.addressComponents[0] = new AddressComponent();
        gr.addressComponents[0].longName = "Foo Bar";
        gr.addressComponents[0].shortName = "Foo";
        gr.addressComponents[0].types = new AddressComponentType[1];
        gr.addressComponents[0].types[0] = AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1;
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemResultEmptyAddressComponent() {
        final GeocodingResult gr = new GeocodingResult();
        gr.addressComponents = new AddressComponent[1];
        gr.addressComponents[0] = new AddressComponent();
        gr.addressComponents[0].longName = "Foo Bar";
        gr.addressComponents[0].shortName = "Foo";
        gr.addressComponents[0].types = new AddressComponentType[0];
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemResultAddress() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "formatted address";
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemResultPlaceId() {
        final GeocodingResult gr = new GeocodingResult();
        gr.placeId = "kfdhasfokjhkljdasf";
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemResultPostcodeLocality() {
        final GeocodingResult gr = new GeocodingResult();
        gr.postcodeLocalities = new String[1];
        gr.postcodeLocalities[0] = "foobar";
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemResultAddressType() {
        final GeocodingResult gr = new GeocodingResult();
        gr.types = new AddressType[1];
        gr.types[0] = AddressType.ADMINISTRATIVE_AREA_LEVEL_1;
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemResultEmptyGeometry() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemResultGeometryLocation() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        final double lat = 10.00;
        final double lng = 20.00;
        gr.geometry.location = new LatLng(lat, lng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemResultGeometryEmptyBounds() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.bounds = new Bounds();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> builder.toGeoServiceItem(gci));
    }

    @Test
    void testToBackupGeoCodeItemResultGeometryBoundsWithNE() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.bounds = new Bounds();
        final double lat = 10.00;
        final double lng = 20.00;
        gr.geometry.bounds.northeast = new LatLng(lat, lng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> builder.toGeoServiceItem(gci));
    }

    @Test
    void testToBackupGeoCodeItemResultGeometryBoundsWithSW() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.bounds = new Bounds();
        final double lat = 10.00;
        final double lng = 20.00;
        gr.geometry.bounds.southwest = new LatLng(lat, lng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> builder.toGeoServiceItem(gci));
    }

    @Test
    void testToBackupGeoCodeItemResultGeometryBoundsWithBoth() {
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
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemResultGeometryLocationType() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.locationType = LocationType.APPROXIMATE;
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final GeoServiceItem bgci = builder.toGeoServiceItem(gci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    @Test
    void testToBackupGeoCodeItemResultGeometryEmptyViewport() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.viewport = new Bounds();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> builder.toGeoServiceItem(gci));
    }

    @Test
    void testToBackupGeoCodeItemResultGeometryViewportNE() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.viewport = new Bounds();
        final double lat = 10.00;
        final double lng = 20.00;
        gr.geometry.viewport.northeast = new LatLng(lat, lng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> builder.toGeoServiceItem(gci));
    }

    @Test
    void testToBackupGeoCodeItemResultGeometryViewportSW() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.viewport = new Bounds();
        final double lat = 5.00;
        final double lng = 25.00;
        gr.geometry.viewport.southwest = new LatLng(lat, lng);
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> builder.toGeoServiceItem(gci));
    }

    @Test
    void testToBackupGeoCodeItemResultGeometryViewportBoth() {
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
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }
}
