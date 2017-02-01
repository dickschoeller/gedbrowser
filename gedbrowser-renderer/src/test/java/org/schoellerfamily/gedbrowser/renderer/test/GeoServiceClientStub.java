package org.schoellerfamily.gedbrowser.renderer.test;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * @author Dick Schoeller
 */
public final class GeoServiceClientStub implements GeoServiceClient {
    /**
     * {@inheritDoc}
     */
    @Override
    public GeoServiceItem get(final String placeName) {
        if ("Needham, Massachusetts, USA".equals(placeName)) {
            final FeatureCollection geometry = new FeatureCollection();
            geometry.add(createLocation());
            geometry.add(createBounds());
            geometry.add(createViewport());
            final GeoServiceGeocodingResult result =
                    new GeoServiceGeocodingResult(
                            null, placeName, null, geometry, null, false, null);
            return new GeoServiceItem(placeName, placeName, result);
        }
        if ("Needham, MA, USA".equals(placeName)) {
            final FeatureCollection geometry = new FeatureCollection();
            geometry.add(createLocation());
            final GeoServiceGeocodingResult result =
                    new GeoServiceGeocodingResult(
                            null, placeName, null, geometry, null, false, null);
            return new GeoServiceItem(placeName, placeName, result);
        }
        return new GeoServiceItem(placeName, placeName, null);
    }

    /**
     * @return the location feature
     */
    private Feature createLocation() {
        final Point point = new Point(-71.2377548, 42.2809285);
        final Feature feature = new Feature();
        feature.setGeometry(point);
        return feature;
    }

    /**
     * @return the bounding box feature
     */
    private Feature createBounds() {
        final double confidence = .01;
        final Polygon polygon = new Polygon(
                new LngLatAlt(-71.2377548 - confidence,
                        42.2809285 - confidence),
                new LngLatAlt(-71.2377548 - confidence,
                        42.2809285 + confidence),
                new LngLatAlt(-71.2377548 + confidence,
                        42.2809285 + confidence),
                new LngLatAlt(-71.2377548 + confidence,
                        42.2809285 - confidence),
                new LngLatAlt(-71.2377548 - confidence,
                        42.2809285 - confidence));
        final Feature feature = new Feature();
        feature.setGeometry(polygon);
        return feature;
    }

    /**
     * @return the viewport feature
     */
    private Feature createViewport() {
        final double confidence = .01;
        final Polygon polygon = new Polygon(
                new LngLatAlt(-71.2377548 - confidence,
                        42.2809285 - confidence),
                new LngLatAlt(-71.2377548 - confidence,
                        42.2809285 + confidence),
                new LngLatAlt(-71.2377548 + confidence,
                        42.2809285 + confidence),
                new LngLatAlt(-71.2377548 + confidence,
                        42.2809285 - confidence),
                new LngLatAlt(-71.2377548 - confidence,
                        42.2809285 - confidence));
        final Feature feature = new Feature();
        feature.setGeometry(polygon);
        return feature;
    }

}
