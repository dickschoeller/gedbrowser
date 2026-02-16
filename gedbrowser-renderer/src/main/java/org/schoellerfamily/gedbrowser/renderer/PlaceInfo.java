package org.schoellerfamily.gedbrowser.renderer;

import org.geojson.LngLatAlt;

import lombok.Getter;

/**
 * @author Dick Schoeller
 */
@Getter
public final class PlaceInfo {

    /**
     * The place name to put on the map.
     */
    private final String placeName;

    /**
     * The location to put the pin.
     */
    private final LngLatAlt location;

    /**
     * Southwest corner of the bounding box.
     */
    private final LngLatAlt southwest;

    /**
     * Northeast corner of the bounding box.
     */
    private final LngLatAlt northeast;

    /**
     * @param placeName the place name to put on the map
     * @param latitude the latitude to put the pin
     * @param longitude the longitude to put the pin
     */
    public PlaceInfo(final String placeName, final Double latitude,
            final Double longitude) {
        this.placeName = placeName;
        final Double lat;
        final Double lng;
        if (latitude == null) {
            lat = Double.NaN;
        } else {
            lat = latitude;
        }
        if (longitude == null) {
            lng = Double.NaN;
        } else {
            lng = longitude;
        }
        location = new LngLatAlt(lng, lat);
        if (latitude == null || longitude == null) {
            southwest = new LngLatAlt(Double.NaN, Double.NaN);
            northeast = new LngLatAlt(Double.NaN, Double.NaN);
        } else {
            final double confidence = .01;
            southwest = new LngLatAlt(longitude - confidence, latitude - confidence);
            northeast = new LngLatAlt(longitude + confidence, latitude + confidence);
        }
    }

    /**
     * @param placeName the name of the place
     * @param location the location of the pin
     * @param southwest viewport southwest
     * @param northeast viewport northeast
     */
    public PlaceInfo(final String placeName, final LngLatAlt location,
            final LngLatAlt southwest, final LngLatAlt northeast) {
        this.placeName = placeName;
        this.location = location;
        this.southwest = southwest;
        this.northeast = northeast;
    }

    @Override
    public String toString() {
        if (placeName == null) {
            return String.format(
                    "{ \"placeName\":null, "
                    + "\"latitude\":%4.6f, \"longitude\":%4.6f }",
                    location.getLatitude(), location.getLongitude());
        }
        return String.format(
                "{ \"placeName\":\"%s\", "
                + "\"latitude\":%4.6f, \"longitude\":%4.6f, "
                + "\"southwest\": { \"latitude\":%4.6f,"
                + " \"longitude\":%4.6f }, "
                + "\"northeast\": { \"latitude\":%4.6f,"
                + " \"longitude\":%4.6f } }",
                placeName,
                location.getLatitude(), location.getLongitude(),
                southwest.getLatitude(), southwest.getLongitude(),
                northeast.getLatitude(), northeast.getLongitude());
    }
}
