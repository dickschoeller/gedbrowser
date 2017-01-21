package org.schoellerfamily.gedbrowser.renderer.test;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * @author Dick Schoeller
 */
public class GeoServiceClientStub implements GeoServiceClient {
    /**
     * {@inheritDoc}
     */
    @Override
    public GeoServiceItem get(final String placeName) {
        if ("Needham, Massachusetts, USA".equals(placeName)) {
            final Point point = new Point(-71.2377548, 42.2809285);
            final Feature feature = new Feature();
            feature.setGeometry(point);
            final FeatureCollection geometry = new FeatureCollection();
            geometry.add(feature);
            final GeoServiceGeocodingResult result =
                    new GeoServiceGeocodingResult(
                            null, placeName, null, geometry, null, false, null);
            return new GeoServiceItem(placeName, placeName, result);
        }
        return new GeoServiceItem(placeName, placeName, null);
    }
}
