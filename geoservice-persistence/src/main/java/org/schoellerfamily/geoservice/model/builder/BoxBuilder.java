package org.schoellerfamily.geoservice.model.builder;

import org.geojson.Feature;
import org.schoellerfamily.geoservice.model.GeoServiceBounds;

import com.google.maps.model.Bounds;

/**
 * @author Dick Schoeller
 */
public interface BoxBuilder extends LngLatAltBuilder {
    /**
     * Create a Feature containing a single Polygon describing the bounding box
     * from the provided Bounds.
     *
     * @param id the ID string
     * @param bounds the Bounds
     * @return the Feature
     */
    default Feature toBox(final String id, final Bounds bounds) {
        if (bounds == null) {
            return null;
        }
        if (bounds.southwest == null || bounds.northeast == null) {
            throw new IllegalArgumentException(
                    "Must have legitimate bounding box");
        }
        return GeoServiceBounds.createBounds(id,
                toLngLatAlt(bounds.southwest),
                toLngLatAlt(bounds.northeast));
    }
}
