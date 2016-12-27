package org.schoellerfamily.geoservice.backup.model;

/**
 * @author Dick Schoeller
 */
public final class BackupGeoCodeItem {
    /** */
    private final String placeName;
    /** */
    private final String modernPlaceName;
    /** */
    private final BackupGeocodingResult result;

    /**
     * Default constructor used for serialization.
     */
    public BackupGeoCodeItem() {
        this.placeName = null;
        this.modernPlaceName = null;
        this.result = null;
    }

    /**
     * Constructor.
     *
     * @param placeName the place name
     * @param modernPlaceName a modern version of the placename
     * @param result the result of geocoding
     */
    public BackupGeoCodeItem(final String placeName,
            final String modernPlaceName, final BackupGeocodingResult result) {
        this.placeName = placeName;
        this.modernPlaceName = modernPlaceName;
        this.result = result;
    }

    /**
     * @return the placeName
     */
    public String getPlaceName() {
        return placeName;
    }

    /**
     * @return the modernPlaceName
     */
    public String getModernPlaceName() {
        return modernPlaceName;
    }

    /**
     * @return the result
     */
    public BackupGeocodingResult getResult() {
        return result;
    }
}
