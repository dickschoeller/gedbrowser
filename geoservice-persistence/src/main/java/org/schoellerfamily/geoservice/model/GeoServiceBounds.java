package org.schoellerfamily.geoservice.model;

/**
 * @author Dick Schoeller
 */
public final class GeoServiceBounds {
    /** */
    private final GeoServiceLatLng northeast;

    /** */
    private final GeoServiceLatLng southwest;

    /**
     * Default constructor used in serialization.
     */
    public GeoServiceBounds() {
        this.northeast = new GeoServiceLatLng();
        this.southwest = new GeoServiceLatLng();
    }

    /**
     * Constructor.
     *
     * @param northeast the northeast corner of the bounding box
     * @param southwest the southwest corner of the bounding box
     */
    public GeoServiceBounds(final GeoServiceLatLng northeast,
            final GeoServiceLatLng southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    /**
     * @return the northeast corner of the bounding box
     */
    public GeoServiceLatLng getNortheast() {
        return northeast;
    }

    /**
     * @return the southwest corner of the bounding box
     */
    public GeoServiceLatLng getSouthwest() {
        return southwest;
    }
}
