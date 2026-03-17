package org.schoellerfamily.geoservice.model.builder.test;

import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.apache.commons.collections4.CollectionUtils;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.AddressType;
import com.google.maps.model.Bounds;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import com.google.maps.model.LocationType;

/**
 * Validates geocode values for test expectations.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.SimplifyBooleanReturns", "PMD.UseVarargs",
    "PMD.UnusedPrivateMethod" })
public class GeocodeValidator {
    /**
     * Executes validate.
     *
     * @param result the result
     * @param backupResult the backup result
     * @return the resulting boolean
     */
    protected boolean validate(final GeocodingResult result,
            final GeoServiceGeocodingResult backupResult) {
        if (result == null && backupResult == null) {
            return true;
        }
        if (result == null || backupResult == null) {
            return false;
        }
        if (!validate(result.addressComponents,
                backupResult.getAddressComponents())) {
            return false;
        }
        if (!validate(result.formattedAddress,
                backupResult.getFormattedAddress())) {
            return false;
        }
        if (!validate(result.geometry, backupResult.getGeometry())) {
            return false;
        }
        if (!validate(result.placeId, backupResult.getPlaceId())) {
            return false;
        }
        if (!validate(result.postcodeLocalities,
                backupResult.getPostcodeLocalities())) {
            return false;
        }
        if (!validate(result.types, backupResult.getTypes())) {
            return false;
        }
        return result.partialMatch == backupResult.isPartialMatch();
    }

    private boolean validate(final AddressComponent[] addressComponents,
            final AddressComponent[] backupAddressComponents) {
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
            if (!validate(addressComponents[i], backupAddressComponents[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean validate(final AddressComponent addressComponent,
            final AddressComponent backupAddressComponent) {
        if (addressComponent == null && backupAddressComponent == null) {
            return true;
        }
        if (addressComponent == null || backupAddressComponent == null) {
            return false;
        }
        if (!validate(addressComponent.longName,
                backupAddressComponent.longName)) {
            return false;
        }
        if (!validate(addressComponent.shortName,
                backupAddressComponent.shortName)) {
            return false;
        }
        return validate(addressComponent.types,
                backupAddressComponent.types);
    }

    private boolean validate(final String string, final String backupString) {
        if (string == null && backupString == null) {
            return true;
        }
        if (string == null || backupString == null) {
            return false;
        }
        return string.equals(backupString);
    }

    private boolean validate(final AddressComponentType[] types,
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
            if (!validate(types[i], backupTypes[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean validate(final AddressComponentType type0,
            final AddressComponentType type1) {
        if (type0 == null && type1 == null) {
            return true;
        }
        if (type0 == null || type1 == null) {
            return false;
        }
        return type0.equals(type1);
    }

    private boolean validate(final Geometry geometry,
            final FeatureCollection featureCollection) {
        if (isEmpty(geometry) && isEmpty(featureCollection)) {
            return true;
        }
        if (isEmpty(geometry) || isEmpty(featureCollection)) {
            return false;
        }
        final Feature location;
        if (featureCollection.getFeatures().isEmpty()) {
            location = null;
        } else {
            location = featureCollection.getFeatures().get(0);
        }
        if (location == null) {
            if (!validate(geometry.location, (Point) null)) {
                return false;
            }
            if (!validate(geometry.locationType, (LocationType) null)) {
                return false;
            }
        } else {
            if (!validate(geometry.location, (Point) location.getGeometry())) {
                return false;
            }
            if (!validate(geometry.locationType,
                    location.getProperty("locationType"))) {
                return false;
            }
        }
        if (featureCollection.getFeatures().isEmpty()) {
            if (!validate("bounds", geometry.bounds, (Feature) null)) {
                return false;
            }
            return validate("viewport", geometry.viewport, null);
        } else {
            if (!validate("bounds", geometry.bounds,
                    featureCollection.getFeatures().get(1))) {
                return false;
            }
            return validate("viewport", geometry.viewport,
                    featureCollection.getFeatures().get(2));
        }
    }

    private boolean isEmpty(final Geometry geometry) {
        if (geometry == null) {
            return true;
        }
        final LatLng point = geometry.location;
        if (!isEmpty(point)) {
            return false;
        }
        if (!isEmpty(geometry.bounds)) {
            return false;
        }
        return isEmpty(geometry.viewport);
    }

    private boolean isEmpty(final Bounds bounds) {
        if (bounds == null) {
            return true;
        }
        if (!isEmpty(bounds.northeast)) {
            return false;
        }
        return isEmpty(bounds.southwest);
    }

    private boolean isEmpty(final LatLng point) {
        if (point == null) {
            return true;
        }
        if (!Double.isNaN(point.lat)) {
            return false;
        }
        return Double.isNaN(point.lng);
    }

    private boolean isEmpty(final FeatureCollection featureCollection) {
        if (featureCollection == null) {
            return true;
        }
        final List<Feature> features = featureCollection.getFeatures();
        if (features == null) {
            return true;
        }
        if (features.isEmpty()) {
            return true;
        }
        for (final Feature feature : features) {
            if (!isEmpty(feature)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmpty(final Feature feature) {
        if (feature == null) {
            return true;
        }
        final GeoJsonObject geometry = feature.getGeometry();
        if (geometry == null) {
            return true;
        }
        if (geometry instanceof Point) {
            final Point point = (Point) geometry;
            return (Double.isNaN(point.getCoordinates().getLatitude())
                    && Double.isNaN(point.getCoordinates().getLongitude()));
        } else if (geometry instanceof Polygon) {
            final Polygon poly = (Polygon) geometry;
            final List<List<LngLatAlt>> coordinates = poly.getCoordinates();
            if (CollectionUtils.isNotEmpty(coordinates)) {
                return false;
            }
        }
        return true;
    }

    private boolean validate(final String id, final Bounds bounds,
            final Feature backupBounds) {
        if (bounds == null && backupBounds == null) {
            return true;
        }
        if (bounds == null || backupBounds == null) {
            return false;
        }
        if (!id.equals(backupBounds.getId())) {
            return false;
        }
        if (bounds.northeast == null || bounds.southwest == null) {
            return false;
        }
        final Polygon polygon = (Polygon) backupBounds.getGeometry();
        final List<LngLatAlt> list = polygon.getCoordinates().get(0);
        final LngLatAlt northeast = list.get(2);
        if (!validate(bounds.northeast, northeast)) {
            return false;
        }
        final LngLatAlt southwest = list.get(0);
        return validate(bounds.southwest, southwest);
    }

    /**
     * Executes validate.
     *
     * @param latLng the lat lng
     * @param point the point
     * @return the resulting boolean
     */
    protected boolean validate(final LatLng latLng, final Point point) {
        if (latLng == null && point == null) {
            return true;
        }
        if (latLng == null || point == null) {
            return false;
        }
        final double tolerance = 0.001;
        if (!almostEqual(latLng.lat, point.getCoordinates().getLatitude(),
                tolerance)) {
            return false;
        }
        return almostEqual(latLng.lng, point.getCoordinates().getLongitude(),
                tolerance);
    }

    /**
     * Executes validate.
     *
     * @param latLng the lat lng
     * @param lla the lla
     * @return the resulting boolean
     */
    protected boolean validate(final LatLng latLng, final LngLatAlt lla) {
        if (latLng == null && lla == null) {
            return true;
        }
        if (latLng == null || lla == null) {
            return false;
        }
        final double tolerance = 0.001;
        if (!almostEqual(latLng.lat, lla.getLatitude(), tolerance)) {
            return false;
        }
        return almostEqual(latLng.lng, lla.getLongitude(), tolerance);
    }

    private boolean almostEqual(final double a, final double b,
            final double eps) {
        return Math.abs(a - b) < eps;
    }

    private boolean validate(final LocationType locationType0,
            final LocationType locationType1) {
        if (locationType0 == null && locationType1 == null) {
            return true;
        }
        if (locationType0 == null || locationType1 == null) {
            return false;
        }
        return locationType0.equals(locationType1);
    }

    private boolean validate(final String[] array0, final String[] array1) {
        if (array0 == null && array1 == null) {
            return true;
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

    private boolean validate(final AddressType[] types0,
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
