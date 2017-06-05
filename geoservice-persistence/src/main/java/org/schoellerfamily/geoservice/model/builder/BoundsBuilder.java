package org.schoellerfamily.geoservice.model.builder;

import java.util.List;

import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;

import com.google.maps.model.Bounds;

/**
 * @author Dick Schoeller
 */
public interface BoundsBuilder extends LatLngBuilder {
    /**
     * Create a Bounds from a GeoServiceBounds.
     *
     * @param feature the bounding box feature
     * @return the Bounds
     */
    default Bounds toBounds(final Feature feature) {
        if (feature == null) {
            return null;
        }
        final Polygon polygon = (Polygon) feature.getGeometry();
        final List<List<LngLatAlt>> coordinates = polygon.getCoordinates();
        if (coordinates == null || coordinates.isEmpty()) {
            return new Bounds();
        }
        final List<LngLatAlt> list = coordinates.get(0);
        final Bounds bounds = toBounds(list.get(2), list.get(0));
        return bounds;
    }

    /**
     * Create Bounds from corners.
     *
     * @param northeast the northeast corner
     * @param southwest the southwest corner
     * @return the Bounds
     */
    default Bounds toBounds(final LngLatAlt northeast,
            final LngLatAlt southwest) {
        final Bounds bounds = new Bounds();
        bounds.northeast = toLatLng(northeast);
        bounds.southwest = toLatLng(southwest);
        return bounds;
    }
}
