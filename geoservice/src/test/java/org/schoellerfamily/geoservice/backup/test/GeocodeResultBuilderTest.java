package org.schoellerfamily.geoservice.backup.test;

import org.junit.Assert;
import org.junit.Test;
import org.schoellerfamily.geoservice.backup.GeocodeResultBuilder;
import org.schoellerfamily.geoservice.backup.model.BackupAddressComponent;
import org.schoellerfamily.geoservice.backup.model.BackupBounds;
import org.schoellerfamily.geoservice.backup.model.BackupGeoCodeItem;
import org.schoellerfamily.geoservice.backup.model.BackupGeocodingResult;
import org.schoellerfamily.geoservice.backup.model.BackupGeometry;
import org.schoellerfamily.geoservice.backup.model.BackupLatLng;
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
@SuppressWarnings({ "PMD.UseVarargs",
        "PMD.TooManyMethods", "PMD.GodClass" })
public final class GeocodeResultBuilderTest {
    /** */
    private final GeocodeResultBuilder builder = new GeocodeResultBuilder();

    /** */
    @Test
    public void testToBackupGeoCodeItemNull() {
        Assert.assertNull("Null gets null", builder.toBackupGeoCodeItem(null));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemName() {
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", null);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertEquals("Mismatched name", "XYZZY", bgci.getPlaceName());
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemModernName() {
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", null);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertEquals("Mismatched modern name", "PLUGH",
                bgci.getModernPlaceName());
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemNullResult() {
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", null);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemEmptyResult() {
        final GeocodingResult gr = new GeocodingResult();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
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
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
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
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultAddress() {
        final GeocodingResult gr = new GeocodingResult();
        gr.formattedAddress = "formatted address";
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultPlaceId() {
        final GeocodingResult gr = new GeocodingResult();
        gr.placeId = "kfdhasfokjhkljdasf";
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultPostcodeLocality() {
        final GeocodingResult gr = new GeocodingResult();
        gr.postcodeLocalities = new String[1];
        gr.postcodeLocalities[0] = "foobar";
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultAddressType() {
        final GeocodingResult gr = new GeocodingResult();
        gr.types = new AddressType[1];
        gr.types[0] = AddressType.ADMINISTRATIVE_AREA_LEVEL_1;
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultEmptyGeometry() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
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
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryEmptyBounds() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.bounds = new Bounds();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
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
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
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
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
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
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryLocationType() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.locationType = LocationType.APPROXIMATE;
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToBackupGeoCodeItemResultGeometryEmptyViewport() {
        final GeocodingResult gr = new GeocodingResult();
        gr.geometry = new Geometry();
        gr.geometry.viewport = new Bounds();
        final GeoCodeItem gci = new GeoCodeItem("XYZZY", "PLUGH", gr);
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
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
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
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
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
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
        final BackupGeoCodeItem bgci = builder.toBackupGeoCodeItem(gci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemNull() {
        Assert.assertNull("Null gets null", builder.toGeoCodeItem(null));
    }

    /** */
    @Test
    public void testToGeoCodeItemName() {
        final BackupGeoCodeItem bgci = new BackupGeoCodeItem("XYZZY", "PLUGH",
                null);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertEquals("Mismatched name", "XYZZY", gci.getPlaceName());
    }

    /** */
    @Test
    public void testToGeoCodeItemModernName() {
        final BackupGeoCodeItem bgci = new BackupGeoCodeItem("XYZZY", "PLUGH",
                null);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertEquals("Mismatched modern name", "PLUGH",
                gci.getModernPlaceName());
    }

    /** */
    @Test
    public void testToGeoCodeItemNullResult() {
        final BackupGeoCodeItem bgci = new BackupGeoCodeItem("XYZZY", "PLUGH",
                null);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertNull("Expected null result", gci.getGeocodingResult());
    }

    /** */
    @Test
    public void testToGeoCodeItemEmptyResult() {
        final BackupGeoCodeItem bgci = new BackupGeoCodeItem("XYZZY", "PLUGH",
                new BackupGeocodingResult());
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultAddressComponent() {
        final BackupAddressComponent[] addressComponents =
                new BackupAddressComponent[1];
        final AddressComponentType[] addressComponentTypes =
                new AddressComponentType[1];
        addressComponentTypes[0] =
                AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1;
        addressComponents[0] =
                new BackupAddressComponent("Foo Bar", "Foo",
                        addressComponentTypes);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(
                addressComponents, null, null, null, null, false, null);
        final BackupGeoCodeItem bgci = new BackupGeoCodeItem("XYZZY", "PLUGH",
                bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultEmptyAddressComponent() {
        final BackupAddressComponent[] addressComponents =
                new BackupAddressComponent[1];
        final AddressComponentType[] addressComponentTypes =
                new AddressComponentType[0];
        addressComponents[0] =
                new BackupAddressComponent("Foo Bar", "Foo",
                        addressComponentTypes);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(
                addressComponents, null, null, null, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultAddress() {
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null,
                "formatted address", null, null, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultPlaceId() {
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, null, null, false, "ladkjsfdlaskjfdlfj");
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultPostcodeLocality() {
        final String[] postcodeLocalities = new String[1];
        postcodeLocalities[0] = "foobar";
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                postcodeLocalities, null, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultAddressType() {
        final AddressType[] types = new AddressType[1];
        types[0] = AddressType.ADMINISTRATIVE_AREA_LEVEL_1;
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, null, types, false, null);
        final BackupGeoCodeItem bgci = new BackupGeoCodeItem("XYZZY", "PLUGH",
                bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultEmptyGeometry() {
        final BackupGeometry geometry = new BackupGeometry();
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, geometry, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryLocation() {
        final double lat = 10.00;
        final double lng = 20.00;
        final BackupLatLng location = new BackupLatLng(lat, lng);
        final BackupGeometry geometry =
                new BackupGeometry(null, location, null, null);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, geometry, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryEmptyBounds() {
        final BackupBounds bounds = new BackupBounds();
        final BackupGeometry geometry =
                new BackupGeometry(bounds, null, null, null);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, geometry, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryBoundsWithNE() {
        final double lat = 10.00;
        final double lng = 20.00;
        final BackupLatLng northeast = new BackupLatLng(lat, lng);
        final BackupBounds bounds = new BackupBounds(northeast, null);
        final BackupGeometry geometry =
                new BackupGeometry(bounds, null, null, null);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, geometry, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryBoundsWithSW() {
        final double lat = 10.00;
        final double lng = 20.00;
        final BackupLatLng southwest = new BackupLatLng(lat, lng);
        final BackupBounds bounds = new BackupBounds(null, southwest);
        final BackupGeometry geometry =
                new BackupGeometry(bounds, null, null, null);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, geometry, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryBoundsWithBoth() {
        final double neLat = 10.00;
        final double neLng = 20.00;
        final BackupLatLng northeast = new BackupLatLng(neLat, neLng);
        final double swLat = 5.00;
        final double swLng = 25.00;
        final BackupLatLng southwest = new BackupLatLng(swLat, swLng);
        final BackupBounds bounds = new BackupBounds(northeast, southwest);
        final BackupGeometry geometry =
                new BackupGeometry(bounds, null, null, null);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, geometry, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryLocationType() {
        final BackupGeometry geometry =
                new BackupGeometry(null, null, LocationType.APPROXIMATE, null);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, geometry, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryEmptyViewport() {
        final BackupBounds viewport = new BackupBounds();
        final BackupGeometry geometry =
                new BackupGeometry(null, null, null, viewport);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, geometry, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryViewportNE() {
        final double lat = 10.00;
        final double lng = 20.00;
        final BackupLatLng northeast = new BackupLatLng(lat, lng);
        final BackupBounds viewport = new BackupBounds(northeast, null);
        final BackupGeometry geometry =
                new BackupGeometry(null, null, null, viewport);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, geometry, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryViewportSW() {
        final double lat = 5.00;
        final double lng = 25.00;
        final BackupLatLng southwest = new BackupLatLng(lat, lng);
        final BackupBounds viewport = new BackupBounds(null, southwest);
        final BackupGeometry geometry =
                new BackupGeometry(null, null, null, viewport);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, geometry, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /** */
    @Test
    public void testToGeoCodeItemResultGeometryViewportBoth() {
        final double neLat = 10.00;
        final double neLng = 20.00;
        final BackupLatLng northeast = new BackupLatLng(neLat, neLng);
        final double swLat = 5.00;
        final double swLng = 25.00;
        final BackupLatLng southwest = new BackupLatLng(swLat, swLng);
        final BackupBounds viewport = new BackupBounds(northeast, southwest);
        final BackupGeometry geometry =
                new BackupGeometry(null, null, null, viewport);
        final BackupGeocodingResult bgr = new BackupGeocodingResult(null, null,
                null, geometry, null, false, null);
        final BackupGeoCodeItem bgci =
                new BackupGeoCodeItem("XYZZY", "PLUGH", bgr);
        final GeoCodeItem gci = builder.toGeoCodeItem(bgci);
        Assert.assertTrue(checker(gci.getGeocodingResult(), bgci.getResult()));
    }

    /**
     * @param result a geocoding result
     * @param backupResult a backup geocoding result
     * @return true if they match
     */
    @SuppressWarnings({ "PMD.NPathComplexity", "PMD.CyclomaticComplexity" })
    private boolean checker(final GeocodingResult result,
            final BackupGeocodingResult backupResult) {
        if (result == null && backupResult == null) {
            return true;
        }
        if (result == null || backupResult == null) {
            return false;
        }
        if (!checker(result.addressComponents,
                backupResult.getAddressComponents())) {
            return false;
        }
        if (!checker(result.formattedAddress,
                backupResult.getFormattedAddress())) {
            return false;
        }
        if (!checker(result.geometry, backupResult.getGeometry())) {
            return false;
        }
        if (!checker(result.placeId, backupResult.getPlaceId())) {
            return false;
        }
        if (!checker(result.postcodeLocalities,
                backupResult.getPostcodeLocalities())) {
            return false;
        }
        if (!checker(result.types, backupResult.getTypes())) {
            return false;
        }
        return result.partialMatch == backupResult.isPartialMatch();
    }

    /**
     * @param addressComponents address components from application model
     * @param backupAddressComponents address components from backup model
     * @return true if all match
     */
    private boolean checker(final AddressComponent[] addressComponents,
            final BackupAddressComponent[] backupAddressComponents) {
        if (addressComponents == null && backupAddressComponents == null) {
            return true;
        }
        if (addressComponents == null || backupAddressComponents == null) {
            return false;
        }
        if (addressComponents.length != backupAddressComponents.length) {
            return false;
        }
        for (int i = 0; i < addressComponents.length; i++) {
            if (!checker(addressComponents[i], backupAddressComponents[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param addressComponent an address component
     * @param backupAddressComponent a backup address component
     * @return true if they match
     */
    private boolean checker(final AddressComponent addressComponent,
            final BackupAddressComponent backupAddressComponent) {
        if (addressComponent == null && backupAddressComponent ==  null) {
            return true;
        }
        if (addressComponent == null || backupAddressComponent == null) {
            return false;
        }
        if (!checker(addressComponent.longName,
                backupAddressComponent.getLongName())) {
            return false;
        }
        if (!checker(addressComponent.shortName,
                backupAddressComponent.getShortName())) {
            return false;
        }
        return checker(addressComponent.types,
                backupAddressComponent.getTypes());
    }

    /**
     * @param string string from application model
     * @param backupString string from backup model
     * @return true if strings match
     */
    private boolean checker(final String string, final String backupString) {
        if (string == null && backupString == null) {
            return true;
        }
        if (string == null || backupString == null) {
            return false;
        }
        return string.equals(backupString);
    }

    /**
     * @param types array of types from geocode result
     * @param backupTypes array of types from backup
     * @return true if they match
     */
    private boolean checker(final AddressComponentType[] types,
            final AddressComponentType[] backupTypes) {
        if (types == null && backupTypes == null) {
            return true;
        }
        if (types == null || backupTypes == null) {
            return true;
        }
        if (types.length != backupTypes.length) {
            return false;
        }
        for (int i = 0; i < types.length; i++) {
            if (!checker(types[i], backupTypes[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param type0 type from geocode
     * @param type1 type from backup
     * @return true if they match
     */
    private boolean checker(final AddressComponentType type0,
            final AddressComponentType type1) {
        if (type0 == null && type1 == null) {
            return true;
        }
        if (type0 == null || type1 == null) {
            return false;
        }
        return type0.equals(type1);
    }

    /**
     * @param geometry geocode geometry
     * @param backupGeometry backup geometry
     * @return true if they match
     */
    private boolean checker(final Geometry geometry,
            final BackupGeometry backupGeometry) {
        if (geometry == null && backupGeometry == null) {
            return true;
        }
        if (geometry == null || backupGeometry == null) {
            return false;
        }
        if (!checker(geometry.bounds, backupGeometry.getBounds())) {
            return false;
        }
        if (!checker(geometry.location, backupGeometry.getLocation())) {
            return false;
        }
        if (!checker(geometry.locationType, backupGeometry.getLocationType())) {
            return false;
        }
        return checker(geometry.viewport, backupGeometry.getViewport());
    }

    /**
     * @param bounds geocode boundary
     * @param backupBounds backup boundary
     * @return true if they match
     */
    private boolean checker(final Bounds bounds,
            final BackupBounds backupBounds) {
        if (bounds == null && backupBounds == null) {
            return true;
        }
        if (bounds == null || backupBounds == null) {
            return false;
        }
        if (!checker(bounds.northeast, backupBounds.getNortheast())) {
            return false;
        }
        return checker(bounds.southwest, backupBounds.getSouthwest());
    }

    /**
     * @param latLng geocoding location
     * @param backupLatLng backup location
     * @return true if they match
     */
    private boolean checker(final LatLng latLng,
            final BackupLatLng backupLatLng) {
        if (latLng == null && backupLatLng == null) {
            return true;
        }
        if (latLng == null || backupLatLng == null) {
            return false;
        }
        final double tolerance = 0.001;
        if (!almostEqual(latLng.lat, backupLatLng.getLatitude(), tolerance)) {
            return false;
        }
        return almostEqual(latLng.lng, backupLatLng.getLongitude(), tolerance);
    }

    /**
     * Check if 2 doubles are close to the same.
     *
     * @param a first double
     * @param b second double
     * @param eps tolerance
     * @return true if they are close
     */
    private boolean almostEqual(final double a, final double b,
            final double eps) {
        return Math.abs(a - b) < eps;
    }

    /**
     * @param locationType0 first location type
     * @param locationType1 second location type
     * @return true if they match
     */
    private boolean checker(final LocationType locationType0,
            final LocationType locationType1) {
        if (locationType0 == null && locationType1 == null) {
            return true;
        }
        if (locationType0 == null || locationType1 == null) {
            return false;
        }
        return locationType0.equals(locationType1);
    }

    /**
     * @param array0 string array 1
     * @param array1 string array 2
     * @return true if they match
     */
    private boolean checker(final String[] array0, final String[] array1) {
        if (array0 == null && array1 == null) {
            return  true;
        }
        if (array0 == null || array1 == null) {
            return false;
        }
        if (array0.length != array1.length) {
            return false;
        }
        for (int i = 0; i < array0.length; i++) {
            if (!array0[i].equals(array1[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param types0 first array
     * @param types1 second array
     * @return true if they match
     */
    private boolean checker(final AddressType[] types0,
            final AddressType[] types1) {
        if (types0 == null && types1 == null) {
            return true;
        }
        if (types0 == null || types1 == null) {
            return false;
        }
        if (types0.length != types1.length) {
            return false;
        }
        for (int i = 0; i < types0.length; i++) {
            if (!types0[i].equals(types1[i])) {
                return false;
            }
        }
        return true;
    }
}
