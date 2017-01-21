package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class PlaceInfo {

    /**
     * The place name to put on the map.
     */
    private final String placeName;

    /**
     * The latitude to put the pin.
     */
    private final Double latitude;

    /**
     * The longitude to put the pin.
     */
    private final Double longitude;

    /**
     * @param placeName the place name to put on the map
     * @param latitude the latitude to put the pin
     * @param longitude the longitude to put the pin
     */
    public PlaceInfo(final String placeName, final Double latitude,
            final Double longitude) {
        this.placeName = placeName;
        if (latitude == null) {
            this.latitude = Double.NaN;
        } else {
            this.latitude = latitude;
        }
        if (longitude == null) {
            this.longitude = Double.NaN;
        } else {
            this.longitude = longitude;
        }
    }

    /**
     * @return the place name
     */
    public String getPlaceName() {
        return placeName;
    }

    /**
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (placeName == null) {
            return String.format(
                    "{ \"placeName\":null, "
                    + "\"latitude\":%4.6f, \"longitude\":%4.6f }",
                    latitude, longitude);
        }
        return String.format(
                "{ \"placeName\":\"%s\", "
                + "\"latitude\":%4.6f, \"longitude\":%4.6f }",
                placeName, latitude, longitude);
    }
}
