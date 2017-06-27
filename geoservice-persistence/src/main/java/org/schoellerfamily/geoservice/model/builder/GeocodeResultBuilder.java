package org.schoellerfamily.geoservice.model.builder;

import java.util.Arrays;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceGeometry;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import com.google.maps.model.LocationType;

/**
 * Builder class to convert between our own geocoding results and
 * Google's types.
 *
 * @author Dick Schoeller
 */
public final class GeocodeResultBuilder
        implements BoundsBuilder, BoxBuilder, PointBuilder, FeatureBuilder {
    /**
     * Create a GeoCodeItem from a GeoServiceItem.
     *
     * @param gsItem the GeoServiceItem
     * @return the GeoCodeItem
     */
    public GeoCodeItem toGeoCodeItem(final GeoServiceItem gsItem) {
        if (gsItem == null) {
            return null;
        }
        return new GeoCodeItem(gsItem.getPlaceName(),
                gsItem.getModernPlaceName(),
                toGeocodingResult(gsItem.getResult()));
    }

    /**
     * Create a GeocodingResult from a GeoServiceGeocodingResult.
     *
     * @param gsResult the GeoServiceGeocodingResult
     * @return the GeocodingResult
     */
    public GeocodingResult toGeocodingResult(
            final GeoServiceGeocodingResult gsResult) {
        if (gsResult == null) {
            return null;
        }
        final GeocodingResult result = new GeocodingResult();
        final AddressComponent[] addressComponents =
                gsResult.getAddressComponents();
        if (addressComponents == null) {
            result.addressComponents = null;
        } else {
            result.addressComponents =
                    new AddressComponent[addressComponents.length];
            for (int i = 0; i < addressComponents.length; i++) {
                result.addressComponents[i] = copy(addressComponents[i]);
            }
        }
        result.formattedAddress = gsResult.getFormattedAddress();
        result.geometry = toGeometry(gsResult.getGeometry());
        result.partialMatch = gsResult.isPartialMatch();
        result.placeId = gsResult.getPlaceId();
        // This is safe because the gs object returns a copy of its array.
        result.postcodeLocalities = gsResult.getPostcodeLocalities();
        // This is safe because the gs object returns a copy of its array.
        result.types = gsResult.getTypes();
        return result;
    }

    /**
     * Copy an address component. Since they are NOT immutable, I don't
     * want to mess with the variability of the damn things.
     *
     * @param in the component to copy
     * @return the copy
     */
    private AddressComponent copy(final AddressComponent in) {
        final AddressComponent out = new AddressComponent();
        out.longName = in.longName;
        out.shortName = in.shortName;
        out.types = Arrays.copyOf(in.types, in.types.length);
        return out;
    }

    /**
     * Create a Geometry from a GeoServiceGeometry.
     *
     * @param featureCollection the feature collection representing the geometry
     * @return the Geometry
     */
    public Geometry toGeometry(final FeatureCollection featureCollection) {
        if (featureCollection == null) {
            return null;
        }
        final Geometry geometry = new Geometry();
        final Feature location =
                populateBoundaries(geometry, featureCollection);
        populateLocation(geometry, location);
        return geometry;
    }

    /**
     * @param geometry the geometry to populate
     * @param featureCollection the feature collection to get bounds from
     * @return the populated location
     */
    private Feature populateBoundaries(final Geometry geometry,
            final FeatureCollection featureCollection) {
        Feature location;
        if (featureCollection.getFeatures().isEmpty()) {
            location = null;
            geometry.bounds = toBounds(null);
            geometry.viewport = toBounds(null);
        } else {
            location = featureCollection.getFeatures().get(0);
            geometry.bounds = toBounds(featureCollection.getFeatures().get(1));
            geometry.viewport =
                    toBounds(featureCollection.getFeatures().get(2));
        }
        return location;
    }

    /**
     * @param geometry geometry object to  fill in
     * @param location the location to fill it with
     */
    private void populateLocation(final Geometry geometry,
            final Feature location) {
        if (location == null) {
            geometry.location = null;
            geometry.locationType = null;
        } else {
            geometry.location = toLatLng((Point) location.getGeometry());
            geometry.locationType =
                    toLocationType(location.getProperty("locationType"));
        }
    }

    /**
     * Convert a property to a Google LocationType.
     *
     * @param property we can expect this to be a string
     * @return the LocationType
     */
    private LocationType toLocationType(final Object property) {
        if (property == null) {
            return null;
        }
        return LocationType.valueOf(property.toString());
    }

    /**
     * Create a GeoServiceItem from a GeoCodeItem.
     *
     * @param item the GeoCodeItem
     * @return the GeoServiceItem
     */
    public GeoServiceItem toGeoServiceItem(final GeoCodeItem item) {
        if (item == null) {
            return null;
        }
        return new GeoServiceItem(item.getPlaceName(),
                item.getModernPlaceName(),
                toGeoServiceGeocodingResult(item.getGeocodingResult()));
    }

    /**
     * Create a GeoServiceGeocodingResult from a GeocodingResult.
     *
     * @param result the GeocodingResult
     * @return the GeoServiceGeocodingResult
     */
    public GeoServiceGeocodingResult toGeoServiceGeocodingResult(
            final GeocodingResult result) {
        if (result == null) {
            return null;
        }
        return new GeoServiceGeocodingResult(
                result.addressComponents,
                result.formattedAddress,
                result.postcodeLocalities,
                toGeoServiceGeometry(result.geometry),
                result.types,
                result.partialMatch,
                result.placeId);
    }

    /**
     * Create a GeoServiceGeometry from a Geometry.
     *
     * @param geometry the Geometry
     * @return the GeoServiceGeometry
     */
    public FeatureCollection toGeoServiceGeometry(final Geometry geometry) {
        if (geometry == null) {
            return GeoServiceGeometry.createFeatureCollection(
                    toLocationFeature(new LatLng(Double.NaN, Double.NaN),
                            LocationType.UNKNOWN),
                    null, null);
        }
        return GeoServiceGeometry.createFeatureCollection(
                toLocationFeature(geometry.location, geometry.locationType),
                toBox("bounds", geometry.bounds),
                toBox("viewport", geometry.viewport));
    }
}
