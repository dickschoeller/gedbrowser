package org.schoellerfamily.geoservice.model;

/**
 * @author Dick Schoeller
 */
public final class GeoServiceItem {
    /** */
    private final String placeName;
    /** */
    private final String modernPlaceName;
    /** */
    private final GeoServiceGeocodingResult result;

    /**
     * Default constructor used for serialization.
     */
    public GeoServiceItem() {
        this.placeName = null;
        this.modernPlaceName = null;
        this.result = null;
    }

    /**
     * Creates a new GeoServiceItem.
     *
     * @param placeName the place name to use
     * @param modernPlaceName the modern place name to use
     * @param result the result
     */
    public GeoServiceItem(final String placeName, final String modernPlaceName,
            final GeoServiceGeocodingResult result) {
        this.placeName = placeName;
        this.modernPlaceName = modernPlaceName;
        this.result = result;
    }

    /**
     * Gets the place name.
     *
     * @return the place name
     */
    public String getPlaceName() {
        return placeName;
    }

    /**
     * Gets the modern place name.
     *
     * @return the modern place name
     */
    public String getModernPlaceName() {
        return modernPlaceName;
    }

    /**
     * Gets the result.
     *
     * @return the result
     */
    public GeoServiceGeocodingResult getResult() {
        return result;
    }
}
