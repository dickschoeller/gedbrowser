package org.schoellerfamily.geoservice.model.builder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.junit.Test;
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
public final class GeocodeResultBuilderTest extends GeocodeChecker {
    /** */
    private final GeocodeResultBuilder builder = new GeocodeResultBuilder();

    /** */
    @Test
    public void testToGeoCodeItemNull() {
        assertNull("Null gets null", builder.toGeoCodeItem(null));
    }

    /** */
    @Test
    public void testToGeoCodeItemName() {
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", null);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertEquals("Mismatched name", "XYZZY", gci.getPlaceName());
    }

    /** */
    @Test
    public void testToGeoCodeItemModernName() {
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", null);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertEquals("Mismatched modern name", "PLUGH",
                gci.getModernPlaceName());
    }

    /** */
    @Test
    public void testToGeoCodeItemNullResult() {
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", null);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertNull("Expected null result", gci.getGeocodingResult());
    }

    /** */
    @Test
    public void testToGeoCodeItemEmptyResult() {
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH",
                new GeoServiceGeocodingResult());
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultAddressComponent() {
        final AddressComponent[] addressComponents =
                new AddressComponent[1];
        final AddressComponentType[] addressComponentTypes =
                new AddressComponentType[1];
        addressComponentTypes[0] =
                AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1;
        addressComponents[0] = createAddressComponent("Foo Bar", "Foo",
                addressComponentTypes);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                addressComponents, null, null, null, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultEmptyAddressComponent() {
        final AddressComponent[] addressComponents =
                new AddressComponent[1];
        final AddressComponentType[] addressComponentTypes =
                new AddressComponentType[1];
        addressComponents[0] = createAddressComponent("Foo Bar", "Foo",
                addressComponentTypes);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                addressComponents, null, null, null, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /**
     * @param longName long name
     * @param shortName short name
     * @param types types
     * @return the new address component
     */
    @SuppressWarnings("PMD.UseVarargs")
    private AddressComponent createAddressComponent(final String longName,
            final String shortName, final AddressComponentType[] types) {
        final AddressComponent component = new AddressComponent();
        component.longName = longName;
        component.shortName = shortName;
        component.types = Arrays.copyOf(types, types.length);
        return component;
    }

    /** */
    @Test
    public void testToGeoCodeItemResultAddress() {
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                null, "formatted address", null, null, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultPlaceId() {
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                null, null, null, null, null, false, "ladkjsfdlaskjfdlfj");
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultPostcodeLocality() {
        final String[] postcodeLocalities = new String[1];
        postcodeLocalities[0] = "foobar";
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                null, null, postcodeLocalities, null, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultAddressType() {
        final AddressType[] types = new AddressType[1];
        types[0] = AddressType.ADMINISTRATIVE_AREA_LEVEL_1;
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                null, null, null, null, types, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultEmptyGeometry() {
        final FeatureCollection geometry = new FeatureCollection();
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                null, null, null, geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryLocation() {
        final double lat = 10.00;
        final double lng = 20.00;
        final Point point = new Point(lng, lat);
        final FeatureCollection geometry = GeoServiceGeometry
                .createFeatureCollection(null, point, null, null);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                null, null, null, geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryEmptyBounds() {
        final Feature bounds = GeoServiceBounds.createBounds("bounds");
        final FeatureCollection geometry = GeoServiceGeometry
                .createFeatureCollection(bounds, null, null, null);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                null, null, null, geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryBoundsWithNE() {
        final double lat = 10.00;
        final double lng = 20.00;
        final LngLatAlt northeast = new LngLatAlt(lng, lat);
        try {
            final Feature bounds = GeoServiceBounds.createBounds("bounds", null,
                northeast);
            fail("Should have thrown illegal argument exception: " + bounds);
        } catch (IllegalArgumentException e) {
            // Should have thrown
        }
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryBoundsWithSW() {
        final double lat = 10.00;
        final double lng = 20.00;
        final LngLatAlt southwest = new LngLatAlt(lng, lat);
        try {
            final Feature bounds = GeoServiceBounds.createBounds("bounds",
                    southwest, null);
            fail("Should have thrown illegal argument exception: " + bounds);
        } catch (IllegalArgumentException e) {
            // Should have thrown
        }
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryBoundsWithBoth() {
        final double neLat = 10.00;
        final double neLng = 20.00;
        final LngLatAlt northeast = new LngLatAlt(neLng, neLat);
        final double swLat = 5.00;
        final double swLng = 25.00;
        final LngLatAlt southwest = new LngLatAlt(swLng, swLat);
        final Feature bounds = GeoServiceBounds.createBounds("bounds",
                southwest, northeast);
        final FeatureCollection geometry = GeoServiceGeometry
                .createFeatureCollection(bounds, null, null, null);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                null, null, null, geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryLocationType() {
        final FeatureCollection geometry = GeoServiceGeometry
                .createFeatureCollection(null, null, LocationType.APPROXIMATE,
                        null);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                null, null, null, geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryEmptyViewport() {
        final Feature viewport = GeoServiceBounds.createBounds("viewport");
        final FeatureCollection geometry = GeoServiceGeometry
                .createFeatureCollection(null, null, null, viewport);
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                null, null, null, geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryViewportNE() {
        final double lat = 10.00;
        final double lng = 20.00;
        final LngLatAlt northeast = new LngLatAlt(lng, lat);
        try {
            final Feature viewport = GeoServiceBounds.createBounds("viewport",
                    null, northeast);
            fail("Should have thrown illegal argument exception: " + viewport);
        } catch (IllegalArgumentException e) {
            // Should have thrown
        }
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryViewportSW() {
        final double lat = 5.00;
        final double lng = 25.00;
        final LngLatAlt southwest = new LngLatAlt(lng, lat);
        try {
            final Feature viewport = GeoServiceBounds.createBounds("viewport",
                    southwest, null);
            fail("Should have thrown illegal argument exception: " + viewport);
        } catch (IllegalArgumentException e) {
            // Should have thrown
        }
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryViewportBoth() {
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
        final GeoServiceGeocodingResult bgr = new GeoServiceGeocodingResult(
                null, null, null, geometry, null, false, null);
        final GeoServiceItem bgci = new GeoServiceItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        assertTrue("Failed comparison",
                checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testPointToLatLng() {
        final Point point = new Point(1.0, 2.0);
        final LatLng latLng = builder.toLatLng(point);
        assertTrue("coordinates should match", checker(latLng, point));
    }

    /** */
    @Test
    public void testPointToLatLngNull() {
        assertNull("Expected null", builder.toLatLng((Point) null));
    }

    /** */
    @Test
    public void testLngLatAltToLatLng() {
        final LngLatAlt lngLatAlt = new LngLatAlt(1.0, 2.0);
        final LatLng latLng = builder.toLatLng(lngLatAlt);
        assertTrue("coordinates should match", checker(latLng, lngLatAlt));
    }

    /** */
    @Test
    public void testLngLatAltToLatLngNull() {
        assertNull("Expected null", builder.toLatLng((LngLatAlt) null));
    }

    /** */
    @Test
    public void testLatLngToPoint() {
        final LatLng latLng = new LatLng(1.0, 2.0);
        final Point point = builder.toPoint(latLng);
        assertTrue("coordinates should match", checker(latLng, point));
    }

    /** */
    @Test
    public void testLatLngToPointNull() {
        assertNull("Expected null", builder.toPoint((LatLng) null));
    }

    /** */
    @Test
    public void testLatLngToLngLatAlt() {
        final LatLng latLng = new LatLng(1.0, 2.0);
        final LngLatAlt lngLatAlt = builder.toLngLatAlt(latLng);
        assertTrue("coordinates should match", checker(latLng, lngLatAlt));
    }

    /** */
    @Test
    public void testLatLngToLngLatAltNull() {
        assertNull("Expected null", builder.toLngLatAlt((LatLng) null));
    }
}
