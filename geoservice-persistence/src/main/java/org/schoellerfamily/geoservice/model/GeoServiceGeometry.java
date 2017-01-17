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

    /**
     * Constructor.
     *
     * @param bounds the actual bounding box of the region
     * @param location the location of the center of the region
     * @param locationType the level of certainty of the location
     * @param viewport the bounding box of the recommended view of the region
     */
    private GeoServiceGeometry(final Feature bounds,
            final Point location, final LocationType locationType,
            final Feature viewport) {
        this(createLocation(location, locationType), bounds, viewport);
    }

    /**
     * Convert a Point and LocationType into a Feature.
     *
     * @param point the Point
     * @param locationType the LocationType
     * @return the Feature
     */
    private static Feature createLocation(final Point point,
            final LocationType locationType) {
        final Feature location = new Feature();
        location.setGeometry(point);
        location.setProperty("locationType", locationType);
        location.setId("location");
        return location;
    }

    /**
     * Constructor.
     * @param location the location of the center of the region
     * @param bounds the actual bounding box of the region
     * @param viewport the bounding box of the recommended view of the region
     */
    private GeoServiceGeometry(final Feature location, final Feature bounds,
            final Feature viewport) {
        this();
        this.featureCollection.add(location);
        this.featureCollection.add(bounds);
        this.featureCollection.add(viewport);
    }

    /**
     * @return the collection of features that make up this geometry
     */
    private FeatureCollection getFeatureCollection() {
        return featureCollection;
    }

    /**
     * @param bounds the actual bounding box of the region
     * @param location the location of the center of the region
     * @param locationType the level of certainty of the location
     * @param viewport the bounding box of the recommended view of the region
     * @return the feature collection
     */
    public static FeatureCollection createFeatureCollection(
            final Feature bounds, final Point location,
            final LocationType locationType, final Feature viewport) {
        final GeoServiceGeometry gsg = new GeoServiceGeometry(
                bounds, location, locationType, viewport);
        return gsg.getFeatureCollection();
    }
    /**
     * @param location the location of the center of the region
     * @param bounds the actual bounding box of the region
     * @param viewport the bounding box of the recommended view of the region
     * @return the feature collection
     */
    public static FeatureCollection createFeatureCollection(
            final Feature location, final Feature bounds,
            final Feature viewport) {
        final GeoServiceGeometry gsg =
                new GeoServiceGeometry(location, bounds, viewport);
        return gsg.getFeatureCollection();
    }
}
