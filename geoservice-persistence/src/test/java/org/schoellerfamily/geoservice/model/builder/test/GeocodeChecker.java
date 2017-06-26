package org.schoellerfamily.geoservice.model.builder.test;

import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;

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
@SuppressWarnings("PMD.GodClass")
public class GeocodeChecker {
    /**
     * @param result a geocoding result
     * @param backupResult a backup geocoding result
     * @return true if they match
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
    protected boolean checker(final GeocodingResult result,
            final GeoServiceGeocodingResult backupResult) {
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
    @SuppressWarnings("PMD.UseVarargs")
    private boolean checker(final AddressComponent[] addressComponents,
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
            final AddressComponent backupAddressComponent) {
        if (addressComponent == null && backupAddressComponent == null) {
            return true;
        }
        if (addressComponent == null || backupAddressComponent == null) {
            return false;
        }
        if (!checker(addressComponent.longName,
                backupAddressComponent.longName)) {
            return false;
        }
        if (!checker(addressComponent.shortName,
                backupAddressComponent.shortName)) {
            return false;
        }
        return checker(addressComponent.types,
                backupAddressComponent.types);
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
    @SuppressWarnings("PMD.UseVarargs")
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
     * @param featureCollection the collection that represents the geometry
     * @return true if they match
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity",
        "PMD.ModifiedCyclomaticComplexity",
        "PMD.NPathComplexity",
        "PMD.StdCyclomaticComplexity" })
    private boolean checker(final Geometry geometry,
            final FeatureCollection featureCollection) {
        if (isEmpty(geometry) && isEmpty(featureCollection)) {
            return true;
        }
        if (isEmpty(geometry) || isEmpty(featureCollection)) {
            return false;
        }
        Feature location;
        if (featureCollection.getFeatures().isEmpty()) {
            location = null;
        } else {
            location = featureCollection.getFeatures().get(0);
        }
        if (location == null) {
            if (!checker(geometry.location, (Point) null)) {
                return false;
            }
            if (!checker(geometry.locationType, (LocationType) null)) {
                return false;
            }
        } else {
            if (!checker(geometry.location, (Point) location.getGeometry())) {
                return false;
            }
            if (!checker(geometry.locationType,
                    location.getProperty("locationType"))) {
                return false;
            }
        }
        if (featureCollection.getFeatures().isEmpty()) {
            if (!checker("bounds", geometry.bounds, (Feature) null)) {
                return false;
            }
            return checker("viewport", geometry.viewport, null);
        } else {
            if (!checker("bounds", geometry.bounds,
                    featureCollection.getFeatures().get(1))) {
                return false;
            }
            return checker("viewport", geometry.viewport,
                    featureCollection.getFeatures().get(2));
        }
    }

    /**
     * @param geometry the geometry
     * @return if there is no data in it
     */
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

    /**
     * @param bounds a bounding box
     * @return true if null or both points empty
     */
    private boolean isEmpty(final Bounds bounds) {
        if (bounds == null) {
            return true;
        }
        if (!isEmpty(bounds.northeast)) {
            return false;
        }
        return isEmpty(bounds.southwest);
    }

    /**
     * @param point a point
     * @return true if null or both axes not a number
     */
    private boolean isEmpty(final LatLng point) {
        if (point == null) {
            return true;
        }
        if (!Double.isNaN(point.lat)) {
            return false;
        }
        return Double.isNaN(point.lng);
    }

    /**
     * There are a number of ways that a feature collection can be empty.
     *
     * @param featureCollection the feature collection
     * @return true if considered empty
     */
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

    /**
     * @param feature the feature
     * @return if it's empty
     */
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
            if (coordinates != null && !coordinates.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param id id string to look for in the bounds feature
     * @param bounds geocode boundary
     * @param backupBounds backup boundary
     * @return true if they match
     */
    private boolean checker(final String id, final Bounds bounds,
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
        if (!checker(bounds.northeast, northeast)) {
            return false;
        }
        final LngLatAlt southwest = list.get(0);
        return checker(bounds.southwest, southwest);
    }

    /**
     * @param latLng geocoding location
     * @param point GeoJSON Point version of location
     * @return true if they match
     */
    protected boolean checker(final LatLng latLng, final Point point) {
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
     * @param latLng geocoding location
     * @param lla GeoJSON LngLatAlt version of location
     * @return true if they match
     */
    protected boolean checker(final LatLng latLng, final LngLatAlt lla) {
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
    @SuppressWarnings("PMD.UseVarargs")
    private boolean checker(final String[] array0, final String[] array1) {
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

    /**
     * @param types0 first array
     * @param types1 second array
     * @return true if they match
     */
    @SuppressWarnings("PMD.UseVarargs")
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
