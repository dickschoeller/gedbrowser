package org.schoellerfamily.geoservice.model;

import java.util.ArrayList;
import java.util.List;

import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;

/**
 * Helper class which is used by builders to create a clean bounding box
 * GeoJSON feature.
 *
 * @author Dick Schoeller
 */
public final class GeoServiceBounds {
    /** Index in bounding box for SW longitude. */
    private static final int SW_LNG = 0;
    /** Index in bounding box for SW latitude. */
    private static final int SW_LAT = 1;
    /** Index in bounding box for NE longitude. */
    private static final int NE_LNG = 2;
    /** Index in bounding box for NE latitude. */
    private static final int NE_LAT = 3;

    /**
     * Default constructor used in serialization.
     */
    private GeoServiceBounds() {
    }

    /**
     * Build a bounding box feature.
     * In this case, it has a name but is empty.
     *
     * @param id the ID string
     * @return the Feature for the new bounds
     */
    public static Feature createBounds(final String id) {
        final Feature feature = new Feature();
        feature.setId(id);
        feature.setGeometry(new Polygon());
        return feature;
    }

    /**
     * Build a bounding box feature.
     *
     * @param id the ID string
     * @param southwest the southwest corner of the bounding box
     * @param northeast the northeast corner of the bounding box
     * @return the Feature for the new bounds
     */
    public static Feature createBounds(final String id,
            final LngLatAlt southwest,
            final LngLatAlt northeast) {
        final Feature feature = new Feature();
        feature.setId(id);
        if (northeast == null || southwest == null) {
            throw new IllegalArgumentException(
                    "Must have a proper bounding box");
        }
        final double[] bbox = {
                southwest.getLongitude(),
                southwest.getLatitude(),
                northeast.getLongitude(),
                northeast.getLatitude()
        };
        final Polygon polygon = new Polygon();
        feature.setGeometry(polygon);
        polygon.setBbox(bbox);
        final List<LngLatAlt> elements = new ArrayList<>(5);
        elements.add(new LngLatAlt(bbox[SW_LNG], bbox[SW_LAT]));
        elements.add(new LngLatAlt(bbox[NE_LNG], bbox[SW_LAT]));
        elements.add(new LngLatAlt(bbox[NE_LNG], bbox[NE_LAT]));
        elements.add(new LngLatAlt(bbox[SW_LNG], bbox[NE_LAT]));
        elements.add(new LngLatAlt(bbox[SW_LNG], bbox[SW_LAT]));
        polygon.add(elements);
        feature.setBbox(bbox);
        return feature;
    }
}
