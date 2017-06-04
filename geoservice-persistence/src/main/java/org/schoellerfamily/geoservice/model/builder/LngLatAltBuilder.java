package org.schoellerfamily.geoservice.model.builder;

import org.geojson.LngLatAlt;

import com.google.maps.model.LatLng;

/**
 * @author Dick Schoeller
 */
public interface LngLatAltBuilder {
    /**
     * Create a GeoJSON Point from a LatLng.
     *
     * @param latLng the LatLng
     * @return the GeoServiceLatLng
     */
    default LngLatAlt toLngLatAlt(final LatLng latLng) {
        if (latLng == null) {
            return null;
        }
        return new LngLatAlt(latLng.lng, latLng.lat);
    }
}
