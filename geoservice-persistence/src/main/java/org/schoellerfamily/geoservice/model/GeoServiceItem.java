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
     * Constructor.
     *
     * @param placeName the place name
     * @param modernPlaceName a modern version of the placename
     * @param result the result of geocoding
     */
    public GeoServiceItem(final String placeName, final String modernPlaceName,
            final GeoServiceGeocodingResult result) {
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
    public GeoServiceGeocodingResult getResult() {
        return result;
    }
}
