package org.schoellerfamily.gedbrowser.geocodecache;

import com.google.maps.model.GeocodingResult;

/**
 * This class implements a single entry in the geocode cache.
 *
 * @author Dick Schoeller
 */
public class GeoCodeCacheEntry {
    /** The historical place name. */
    private final String placeName;
    /** A modern place name to use for geo-coding. */
    private final String modernPlaceName;
    /** The geo-coding result. */
    private final GeocodingResult geocodingResult;

    /**
     * @param placeName a place name to use for both historical and modern name
     * @param geocodingResult a geo-coding result
     */
    public GeoCodeCacheEntry(final String placeName,
            final GeocodingResult geocodingResult) {
        this.placeName = placeName;
        this.modernPlaceName = placeName;
        this.geocodingResult = geocodingResult;
    }

    /**
     * @param placeName the historical place name
     * @param modernPlaceName the modern place name to use for geo-coding
     * @param geocodingResult the geo-coding result
     */
    public GeoCodeCacheEntry(final String placeName,
            final String modernPlaceName,
            final GeocodingResult geocodingResult) {
        this.placeName = placeName;
        this.modernPlaceName = modernPlaceName;
        this.geocodingResult = geocodingResult;
    }

    /**
     * @param placeName a place name to use for both historical and modern name
     */
    public GeoCodeCacheEntry(final String placeName) {
        this.placeName = placeName;
        this.modernPlaceName = placeName;
        this.geocodingResult = null;
    }

    /**
     * @param placeName the historical place name
     * @param modernPlaceName the modern place name to use for geo-coding
     */
    public GeoCodeCacheEntry(final String placeName,
            final String modernPlaceName) {
        this.placeName = placeName;
        this.modernPlaceName = modernPlaceName;
        this.geocodingResult = null;
    }

    /**
     * @return the historical place name
     */
    public final String getPlaceName() {
        return placeName;
    }

    /**
     * @return the modern place name to use for geo-coding
     */
    public final String getModernPlaceName() {
        return modernPlaceName;
    }

    /**
     * @return the geo-coding result
     */
    public final GeocodingResult getGeocodingResult() {
        return geocodingResult;
    }
}
