package org.schoellerfamily.gedbrowser.renderer;

import java.util.List;
import java.util.Map;

import org.geojson.LngLatAlt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * @author Dick Schoeller
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
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
     * Creates a new PlaceInfo.
     *
     * @param placeName the place name to use
     * @param latitude the latitude
     * @param longitude the longitude
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
     * Creates a new PlaceInfo.
     *
     * @param placeName the place name to use
     * @param location the location
     * @param southwest the southwest
     * @param northeast the northeast
     */
    @JsonCreator
    public PlaceInfo(
            @JsonProperty("placeName") final String placeName,
            @JsonProperty("location") final Object location,
            @JsonProperty("southwest") final Object southwest,
            @JsonProperty("northeast") final Object northeast) {
        this.placeName = placeName;
        this.location = toLngLatAlt(location);
        this.southwest = toLngLatAlt(southwest);
        this.northeast = toLngLatAlt(northeast);
    }

    private static LngLatAlt toLngLatAlt(final Object value) {
        if (value == null) {
            // Default to a non-null LngLatAlt with NaN coordinates to avoid NPEs
            return new LngLatAlt(Double.NaN, Double.NaN);
        }
        if (value instanceof LngLatAlt lngLatAlt) {
            return lngLatAlt;
        }
        if (value instanceof List<?> list) {
            return fromList(list);
        }
        if (value instanceof Object[] arr) {
            return fromList(List.of(arr));
        }
        if (value instanceof Map<?, ?> map) {
            return fromMap(map);
        }
        // For unsupported payload shapes, also default to NaN coordinates
        return new LngLatAlt(Double.NaN, Double.NaN);
    }

    private static LngLatAlt fromList(final List<?> coords) {
        if (coords == null || coords.size() < 2) {
            // Not enough data to form coordinates; return sentinel
            return new LngLatAlt(Double.NaN, Double.NaN);
        }
        final Double longitude = asDouble(coords.get(0));
        final Double latitude = asDouble(coords.get(1));
        if (longitude == null || latitude == null) {
            // Invalid numeric values; return sentinel
            return new LngLatAlt(Double.NaN, Double.NaN);
        }
        return new LngLatAlt(longitude, latitude);
    }

    private static LngLatAlt fromMap(final Map<?, ?> map) {
        final Double longitude = firstDouble(map, "longitude", "lng");
        final Double latitude = firstDouble(map, "latitude", "lat");
        if (longitude != null && latitude != null) {
            return new LngLatAlt(longitude, latitude);
        }

        final Object coords = map.get("coordinates");
        if (coords instanceof List<?> list) {
            return fromList(list);
        }
        if (coords instanceof Object[] arr) {
            return fromList(List.of(arr));
        }
        // If we cannot parse coordinates from the map, return sentinel
        return new LngLatAlt(Double.NaN, Double.NaN);
    }

    private static Double firstDouble(final Map<?, ?> map, final String... keys) {
        for (final String key : keys) {
            if (map.containsKey(key)) {
                final Double value = asDouble(map.get(key));
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

    private static Double asDouble(final Object value) {
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        if (value instanceof String text) {
            try {
                return Double.parseDouble(text);
            } catch (NumberFormatException _) {
                return null;
            }
        }
        return null;
    }

    /**
     * Executes to string.
     *
     * @return the resulting string
     */
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
