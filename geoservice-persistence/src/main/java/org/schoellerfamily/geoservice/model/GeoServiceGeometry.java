package org.schoellerfamily.geoservice.model;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;

import com.google.maps.model.LocationType;

/**
 * A variant of Google's Geometry that will work with Jackson serialization to
 * JSON.
 *
 * @author Dick Schoeller
 */
public final class GeoServiceGeometry {
    /**
     * We are trying the features as a collection.
     */
    private final FeatureCollection featureCollection;

    /**
     * Default constructor to use in serialization.
     */
    public GeoServiceGeometry() {
        this.featureCollection = new FeatureCollection();
    }

    private GeoServiceGeometry(final Feature bounds,
            final Point location, final LocationType locationType,
            final Feature viewport) {
        this(createLocation(location, locationType), bounds, viewport);
    }

    private static Feature createLocation(final Point point,
            final LocationType locationType) {
        final Feature location = new Feature();
        location.setGeometry(point);
        location.setProperty("locationType", locationType);
        location.setId("location");
        return location;
    }

    private GeoServiceGeometry(final Feature location, final Feature bounds,
            final Feature viewport) {
        this();
        this.featureCollection.add(location);
        this.featureCollection.add(bounds);
        this.featureCollection.add(viewport);
    }

    private FeatureCollection getFeatureCollection() {
        return featureCollection;
    }

    /**
     * Creates the feature collection.
     *
     * @param bounds the bounds
     * @param location the location
     * @param locationType the location type to use
     * @param viewport the viewport
     * @return the resulting feature collection
     */
    public static FeatureCollection createFeatureCollection(
            final Feature bounds, final Point location,
            final LocationType locationType, final Feature viewport) {
        final GeoServiceGeometry gsg = new GeoServiceGeometry(
                bounds, location, locationType, viewport);
        return gsg.getFeatureCollection();
    }
    /**
     * Creates the feature collection.
     *
     * @param location the location
     * @param bounds the bounds
     * @param viewport the viewport
     * @return the resulting feature collection
     */
    public static FeatureCollection createFeatureCollection(
            final Feature location, final Feature bounds,
            final Feature viewport) {
        final GeoServiceGeometry gsg =
                new GeoServiceGeometry(location, bounds, viewport);
        return gsg.getFeatureCollection();
    }
}
