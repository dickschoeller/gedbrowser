package org.schoellerfamily.geoservice.model.builder;

import org.geojson.Feature;
import org.geojson.Point;

import com.google.maps.model.LatLng;
import com.google.maps.model.LocationType;

/**
 * @author Dick Schoeller
 */
public interface FeatureBuilder {
    /**
     * Create a GeoJSON Point from a LatLng.
     *
     * @param latLng the LatLng
     * @param locationType the location type
     * @return the GeoServiceLatLng
     */
    default Feature toLocationFeature(final LatLng latLng,
            final LocationType locationType) {
        if (latLng == null) {
            final Feature feature = new Feature();
            feature.setProperty("locationType", locationType);
            feature.setId("location");
            return feature;
        }
        final Point point = new Point(latLng.lng, latLng.lat);
        final Feature feature = new Feature();
        feature.setGeometry(point);
        feature.setProperty("locationType", locationType);
        feature.setId("location");
        return feature;
    }
}
