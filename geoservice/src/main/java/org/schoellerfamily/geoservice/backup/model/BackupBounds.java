package org.schoellerfamily.geoservice.backup.model;

/**
 * @author Dick Schoeller
 */
public final class BackupBounds {
    /** */
    private final BackupLatLng northeast;

    /** */
    private final BackupLatLng southwest;

    /**
     * Default constructor used in serialization.
     */
    public BackupBounds() {
        this.northeast = new BackupLatLng();
        this.southwest = new BackupLatLng();
    }

    /**
     * Constructor.
     *
     * @param northeast the northeast corner of the bounding box
     * @param southwest the southwest corner of the bounding box
     */
    public BackupBounds(final BackupLatLng northeast,
            final BackupLatLng southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    /**
     * @return the northeast corner of the bounding box
     */
    public BackupLatLng getNortheast() {
        return northeast;
    }

    /**
     * @return the southwest corner of the bounding box
     */
    public BackupLatLng getSouthwest() {
        return southwest;
    }
}
