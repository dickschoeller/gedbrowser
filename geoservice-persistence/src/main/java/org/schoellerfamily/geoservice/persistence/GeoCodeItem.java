package org.schoellerfamily.geoservice.persistence;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

/**
 * This class implements a single entry in the geocode cache.
 *
 * @author Dick Schoeller
 */
public final class GeoCodeItem {
    /** The historical place name. */
    private final String placeName;
    /** A modern place name to use for geo-coding. */
    private final String modernPlaceName;
    /** The geo-coding result. */
    private final GeocodingResult geocodingResult;

    /**
     * Default constructor.
     */
    public GeoCodeItem() {
        this.placeName = null;
        this.modernPlaceName = null;
        this.geocodingResult = null;
    }

    /**
     * @param placeName a place name to use for both historical and modern name
     * @param geocodingResult a geo-coding result
     */
    public GeoCodeItem(final String placeName,
            final GeocodingResult geocodingResult) {
        this.placeName = placeName;
        this.modernPlaceName = placeName;
        this.geocodingResult = geocodingResult;
    }

    /**
     * @param placeName a place name to use for both historical and modern name
     */
    public GeoCodeItem(final String placeName) {
        this.placeName = placeName;
        this.modernPlaceName = placeName;
        this.geocodingResult = null;
    }

    /**
     * @param placeName the historical place name
     * @param modernPlaceName the modern place name to use for geo-coding
     */
    public GeoCodeItem(final String placeName,
            final String modernPlaceName) {
        this.placeName = placeName;
        this.modernPlaceName = modernPlaceName;
        this.geocodingResult = null;
    }

    /**
     * @return the historical place name
     */
    public String getPlaceName() {
        return placeName;
    }

    /**
     * @return the modern place name to use for geo-coding
     */
    public String getModernPlaceName() {
        return modernPlaceName;
    }

    /**
     * @return the geo-coding result
     */
    public GeocodingResult getGeocodingResult() {
        return geocodingResult;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + hashCode(geocodingResult);
        result = prime * result + hashCode(modernPlaceName);
        result = prime * result + hashCode(placeName);
        return result;
    }

    /**
     * Get the hash code of the gcResult. 0 if object is null.
     *
     * @param gcResult the object
     * @return its hash code
     */
    private int hashCode(final GeocodingResult gcResult) {
        if (gcResult == null) {
            return 0;
        }

        // TODO are any other fields useful?
        final int prime = 31;
        int result = 1;
        result = prime * result + hashCode(gcResult.formattedAddress);
        result = prime * result + hashCode(gcResult.geometry);
        return result;
    }

    /**
     * Get the hash code of the geometry. 0 if object is null.
     *
     * @param geometry the geometry
     * @return the hash code
     */
    private int hashCode(final Geometry geometry) {
        if (geometry == null) {
            return 0;
        }

        // TODO should I expand to look at more data?
        final int prime = 31;
        int result = 1;
        result = prime * result + hashCode(geometry.location);
        return result;
    }

    /**
     * Get the hash code of the location. 0 if object is null.
     *
     * @param location the location
     * @return the hash code
     */
    private int hashCode(final LatLng location) {
        if (location == null) {
            return 0;
        }

        // TODO should this look at the actual values instead of toString?
        return hashCode(location.toString());
    }

    /**
     * Get the hash code of the String. 0 if object is null.
     *
     * @param o the object
     * @return its hash code
     */
    private static int hashCode(final String o) {
        if (o == null) {
            return 0;
        }
        return o.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        // Suppressed all typical problems that go with an equals method
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GeoCodeItem other = (GeoCodeItem) obj;
        if (!equals(geocodingResult, other.geocodingResult)) {
            return false;
        }
        if (!stringCompare(modernPlaceName, other.modernPlaceName)) {
            return false;
        }
        return (stringCompare(placeName, other.placeName));
    }

    /**
     * A relatively shallow equals comparison.
     *
     * @param result the item
     * @param other the item to compare
     * @return returns true if they seem equal
     */
    @SuppressWarnings({ "PMD.CompareObjectsWithEquals" })
    private static boolean equals(final GeocodingResult result,
            final GeocodingResult other) {
        // Suppressed all typical problems that go with an equals method
        if (result == other) {
            return true;
        }
        if (result == null || other == null) {
            return false;
        }
        if (!stringCompare(result.formattedAddress, other.formattedAddress)) {
            return false;
        }
        return geometryCompare(result.geometry, other.geometry);
    }

    /**
     * @param result the input geometry
     * @param other the compared geometry
     * @return true if they match
     */
    private static boolean geometryCompare(final Geometry result,
            final Geometry other) {
        if (result == null) {
            return other == null;
        }
        if (other == null) {
            return false;
        }
        if (result.location == null) {
            return other.location == null;
        }
        if (other.location == null) {
            return false;
        }
        return result.location.toString().equals(other.location.toString());
    }

    /**
     * @param string string from primary
     * @param otherString string from compared object
     * @return true if strings match
     */
    private static boolean stringCompare(final String string,
            final String otherString) {
        if (string == null) {
            if (otherString != null) {
                return false;
            }
        } else if (!string.equals(otherString)) {
            return false;
        }
        return true;
    }

    /**
     * @param placeName the historical place name
     * @param modernPlaceName the modern place name to use for geo-coding
     * @param geocodingResult the geo-coding result
     */
    public GeoCodeItem(final String placeName,
            final String modernPlaceName,
            final GeocodingResult geocodingResult) {
        this.placeName = placeName;
        this.modernPlaceName = modernPlaceName;
        this.geocodingResult = geocodingResult;
    }
}
