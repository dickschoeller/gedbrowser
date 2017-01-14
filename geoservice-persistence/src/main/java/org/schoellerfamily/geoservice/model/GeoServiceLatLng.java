package org.schoellerfamily.geoservice.model;

import java.util.Locale;

import com.google.maps.internal.StringJoin.UrlValue;

/**
 * A variant of Google's LatLng that will work with Jackson serialization to
 * JSON.
 *
 * @author Dick Schoeller
 */
public final class GeoServiceLatLng implements UrlValue {

    /**
     * The latitude of this location.
     */
    private final double latitude;

    /**
     * The longitude of this location.
     */
    private final double longitude;

    /**
     * Construct a location with a 0.0, 0.0 value.
     */
    public GeoServiceLatLng() {
      this.latitude = 0.0;
      this.longitude = 0.0;
    }

    /**
     * Construct a location with a latitude longitude pair.
     *
     * @param lat the latitude
     * @param lng the longitude
     */
    public GeoServiceLatLng(final double lat, final double lng) {
      this.latitude = lat;
      this.longitude = lng;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return toUrlValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toUrlValue() {
      // Enforce Locale to English for double to string conversion
      return String.format(Locale.ENGLISH, "%.8f,%.8f", latitude, longitude);
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }
}
