package org.schoellerfamily.geoservice.model.builder.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.model.GeoServiceBounds;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceGeometry;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.builder.GeocodeResultBuilder;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.AddressType;
import com.google.maps.model.LatLng;
import com.google.maps.model.LocationType;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods" })
public final class GeocodeResultBuilderTest extends GeocodeValidator {
    /** */
    private final GeocodeResultBuilder builder = new GeocodeResultBuilder();

    /** */
    @Test
    void testToGeoCodeItemNull() {
        assertNull(builder.toGeoCodeItem(null), "Null gets null");
    }

    /** */
    @Test
    void testToGeoCodeItemName() {
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", null);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertEquals("XYZZY", gci.getPlaceName(), "Mismatched name");
    }

    /** */
    @Test
    void testToGeoCodeItemModernName() {
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", null);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertEquals("PLUGH", gci.getModernPlaceName(), "Mismatched modern name");
    }

    /** */
    @Test
    void testToGeoCodeItemNullResult() {
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", null);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertNull(gci.getGeocodingResult(), "Expected null result");
    }

    /** */
    @Test
    void testToGeoCodeItemEmptyResult() {
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH",
            new GeoServiceGeocodingResult());
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultAddressComponent() {
        final AddressComponent[] addressComponents = new AddressComponent[1];
        final AddressComponentType[] addressComponentTypes = new AddressComponentType[1];
        addressComponentTypes[0] = AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1;
        addressComponents[0] = createAddressComponent("Foo Bar", "Foo", addressComponentTypes);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(addressComponents, null,
            null, null, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultEmptyAddressComponent() {
        final AddressComponent[] addressComponents = new AddressComponent[1];
        final AddressComponentType[] addressComponentTypes = new AddressComponentType[1];
        addressComponents[0] = createAddressComponent("Foo Bar", "Foo", addressComponentTypes);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(addressComponents, null,
            null, null, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /**
     * @param longName  long name
     * @param shortName short name
     * @param types     types
     * @return the new address component
     */
    @SuppressWarnings("PMD.UseVarargs")
    private AddressComponent createAddressComponent(final String longName, final String shortName,
        final AddressComponentType[] types) {
        final AddressComponent component = new AddressComponent();
        component.longName = longName;
        component.shortName = shortName;
        component.types = Arrays.copyOf(types, types.length);
        return component;
    }

    /** */
    @Test
    void testToGeoCodeItemResultAddress() {
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(null,
            "formatted address", null, null, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultPlaceId() {
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(null, null, null, null,
            null, false, "ladkjsfdlaskjfdlfj");
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultPostcodeLocality() {
        final String[] postcodeLocalities = new String[1];
        postcodeLocalities[0] = "foobar";
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(null, null,
            postcodeLocalities, null, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultAddressType() {
        final AddressType[] types = new AddressType[1];
        types[0] = AddressType.ADMINISTRATIVE_AREA_LEVEL_1;
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(null, null, null, null,
            types, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultEmptyGeometry() {
        final FeatureCollection geometry = new FeatureCollection();
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(null, null, null,
            geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultGeometryLocation() {
        final double lat = 10.00;
        final double lng = 20.00;
        final Point point = new Point(lng, lat);
        final FeatureCollection geometry = GeoServiceGeometry.createFeatureCollection(null, point,
            null, null);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(null, null, null,
            geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultGeometryEmptyBounds() {
        final Feature bounds = GeoServiceBounds.createBounds("bounds");
        final FeatureCollection geometry = GeoServiceGeometry.createFeatureCollection(bounds, null,
            null, null);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(null, null, null,
            geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultGeometryBoundsWithNE() {
        final double lat = 10.00;
        final double lng = 20.00;
        final LngLatAlt northeast = new LngLatAlt(lng, lat);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> GeoServiceBounds.createBounds("bounds", null, northeast));
    }

    /** */
    @Test
    void testToGeoCodeItemResultGeometryBoundsWithSW() {
        final double lat = 10.00;
        final double lng = 20.00;
        final LngLatAlt southwest = new LngLatAlt(lng, lat);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> GeoServiceBounds.createBounds("bounds", southwest, null));
    }

    /** */
    @Test
    void testToGeoCodeItemResultGeometryBoundsWithBoth() {
        final double neLat = 10.00;
        final double neLng = 20.00;
        final LngLatAlt northeast = new LngLatAlt(neLng, neLat);
        final double swLat = 5.00;
        final double swLng = 25.00;
        final LngLatAlt southwest = new LngLatAlt(swLng, swLat);
        final Feature bounds = GeoServiceBounds.createBounds("bounds", southwest, northeast);
        final FeatureCollection geometry = GeoServiceGeometry.createFeatureCollection(bounds, null,
            null, null);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(null, null, null,
            geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultGeometryLocationType() {
        final FeatureCollection geometry = GeoServiceGeometry.createFeatureCollection(null, null,
            LocationType.APPROXIMATE, null);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(null, null, null,
            geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultGeometryEmptyViewport() {
        final Feature viewport = GeoServiceBounds.createBounds("viewport");
        final FeatureCollection geometry = GeoServiceGeometry.createFeatureCollection(null, null,
            null, viewport);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(null, null, null,
            geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testToGeoCodeItemResultGeometryViewportNE() {
        final double lat = 10.00;
        final double lng = 20.00;
        final LngLatAlt northeast = new LngLatAlt(lng, lat);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> GeoServiceBounds.createBounds("viewport", null, northeast));
    }

    /** */
    @Test
    void testToGeoCodeItemResultGeometryViewportSW() {
        final double lat = 5.00;
        final double lng = 25.00;
        final LngLatAlt southwest = new LngLatAlt(lng, lat);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> GeoServiceBounds.createBounds("viewport", southwest, null));
    }

    /** */
    @Test
    void testToGeoCodeItemResultGeometryViewportBoth() {
        final double neLat = 10.00;
        final double neLng = 20.00;
        final LngLatAlt northeast = new LngLatAlt(neLng, neLat);
        final double swLat = 5.00;
        final double swLng = 25.00;
        final LngLatAlt southwest = new LngLatAlt(swLng, swLat);
        final Feature viewport = GeoServiceBounds.createBounds("viewport", southwest, northeast);
        final FeatureCollection geometry = GeoServiceGeometry.createFeatureCollection(null, null,
            null, viewport);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(null, null, null,
            geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue(validate(gci.getGeocodingResult(), bgci.getResult()), "Failed comparison");
    }

    /** */
    @Test
    void testPointToLatLng() {
        final Point point = new Point(1.0, 2.0);
        final LatLng latLng = builder.toLatLng(point);
        assertTrue(validate(latLng, point), "coordinates should match");
    }

    /** */
    @Test
    void testPointToLatLngNull() {
        assertNull(builder.toLatLng((Point) null), "Expected null");
    }

    /** */
    @Test
    void testLngLatAltToLatLng() {
        final LngLatAlt lngLatAlt = new LngLatAlt(1.0, 2.0);
        final LatLng latLng = builder.toLatLng(lngLatAlt);
        assertTrue(validate(latLng, lngLatAlt), "coordinates should match");
    }

    /** */
    @Test
    void testLngLatAltToLatLngNull() {
        assertNull(builder.toLatLng((LngLatAlt) null), "Expected null");
    }

    /** */
    @Test
    void testLatLngToPoint() {
        final LatLng latLng = new LatLng(1.0, 2.0);
        final Point point = builder.toPoint(latLng);
        assertTrue(validate(latLng, point), "coordinates should match");
    }

    /** */
    @Test
    void testLatLngToPointNull() {
        assertNull(builder.toPoint((LatLng) null), "Expected null");
    }

    /** */
    @Test
    void testLatLngToLngLatAlt() {
        final LatLng latLng = new LatLng(1.0, 2.0);
        final LngLatAlt lngLatAlt = builder.toLngLatAlt(latLng);
        assertTrue(validate(latLng, lngLatAlt), "coordinates should match");
    }

    /** */
    @Test
    void testLatLngToLngLatAltNull() {
        assertNull(builder.toLngLatAlt((LatLng) null), "Expected null");
    }
}
