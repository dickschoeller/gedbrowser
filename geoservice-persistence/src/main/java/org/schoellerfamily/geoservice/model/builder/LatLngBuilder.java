package org.schoellerfamily.geoservice.model.builder;

import org.geojson.LngLatAlt;
import org.geojson.Point;

import com.google.maps.model.LatLng;

/**
 * @author Dick Schoeller
 */
public interface LatLngBuilder {
    /**
     * Create a LatLng from a GeoJSON LngLatAlt.
     *
     * @param lla the LngLatAlt
     * @return the LatLng
     */
    default LatLng toLatLng(final LngLatAlt lla) {
        if (lla == null) {
            return null;
        }
        return new LatLng(lla.getLatitude(), lla.getLongitude());
    }

    /**
     * Create a LatLng from a GeoJSON Point.
     *
     * @param point the Point
     * @return the LatLng
     */
    default LatLng toLatLng(final Point point) {
        if (point == null) {
            return null;
        }
        return toLatLng(point.getCoordinates());
    }
}
