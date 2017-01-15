package org.schoellerfamily.geoservice.model;

import org.geojson.Feature;
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
     * {@code bounds} (optionally returned) stores the bounding box which can
     * fully contain the returned result. Note that these bounds may not match
     * the recommended viewport. (For example, San Francisco includes the
     * Farallon islands, which are technically part of the city, but probably
     * should not be returned in the viewport.)
     */
    private final Feature bounds;

    /**
     * {@code location} contains the geocoded {@code latitude,longitude} value.
     * For normal address lookups, this field is typically the most important.
     */
    private final Point location;

    /**
     * The level of certainty of this geocoding result.
     */
    private final LocationType locationType;

    /**
     * {@code viewport} contains the recommended viewport for displaying the
     * returned result. Generally the viewport is used to frame a result when
     * displaying it to a user.
     */
    private final Feature viewport;

    /**
     * Default constructor to use in serialization.
     */
    public GeoServiceGeometry() {
        this.bounds = null;
        this.location = null;
        this.locationType = LocationType.UNKNOWN;
        this.viewport = null;
    }

    /**
     * Constuctor.
     *
     * @param bounds the actual bounding box of the region
     * @param location the location of the center of the region
     * @param locationType the level of certainty of the location
     * @param viewport the bounding box of the recommended view of the region
     */
    public GeoServiceGeometry(final Feature bounds,
            final Point location, final LocationType locationType,
            final Feature viewport) {
        super();
        this.bounds = bounds;
        this.location = location;
        this.locationType = locationType;
        this.viewport = viewport;
    }

    /**
     * @return the actual bounding box of the region
     */
    public Feature getBounds() {
        return bounds;
    }

    /**
     * @return the location of the center of the region
     */
    public Point getLocation() {
        return location;
    }

    /**
     * @return the level of certainty of the location
     */
    public LocationType getLocationType() {
        return locationType;
    }

    /**
     * @return the bounding box of the recommended view of the region
     */
    public Feature getViewport() {
        return viewport;
    }
}
