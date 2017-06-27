package org.schoellerfamily.geoservice.model.builder;

import org.geojson.Point;

import com.google.maps.model.LatLng;

/**
 * @author Dick Schoeller
 */
public interface PointBuilder {
    /**
     * Create a GeoJSON Point from a LatLng.
     *
     * @param latLng the LatLng
     * @return the GeoServiceLatLng
     */
    default Point toPoint(final LatLng latLng) {
        if (latLng == null) {
            return null;
        }
        return new Point(latLng.lng, latLng.lat);
    }
}
