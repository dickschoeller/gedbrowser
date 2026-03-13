package org.schoellerfamily.gedbrowser.renderer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;
import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PlaceVisitor;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceItem;

import lombok.extern.slf4j.Slf4j;

/**
 * Generates the collection of places for a provided person.
 *
 * @author Dick Schoeller
 */
@Slf4j
public final class PlaceListRenderer {
    /** */
    private final Person person;
    /** */
    private final GeoServiceClient client;
    /** */
    private final RenderingContext renderingContext;
    /** */
    private final LivingEstimator le;

    /**
     * @param person the person
     * @param client the geoservice client
     * @param renderingContext the current rendering context
     */
    public PlaceListRenderer(final Person person,
            final GeoServiceClient client,
            final RenderingContext renderingContext) {
        this.person = person;
        this.client = client;
        this.renderingContext = renderingContext;
        this.le = new LivingEstimator(person, renderingContext);
    }

    /**
     * @return the list of place information
     */
    public List<PlaceInfo> render() {
        if (person == null || client == null) {
            return Collections.<PlaceInfo>emptyList();
        }
        if (isHiddenConfidential() || isHiddenLiving()) {
            return Collections.<PlaceInfo>emptyList();
        }
        final PlaceVisitor visitor = new PlaceVisitor();
        person.accept(visitor);
        final Collection<String> places = visitor.getPlaceStrings();
        return geoCodePlaces(places);
    }

    /**
     * @param places the place to code
     * @return the list of geocode results
     */
    private List<PlaceInfo> geoCodePlaces(final Collection<String> places) {
        return places.stream()
                .map(client::get)
                .filter(Objects::nonNull)
                .filter(item -> item.getResult() != null)
                .map(this::createPlaceInfo)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * @param item the geoservice item containing the data
     * @return the new place information
     */
    private PlaceInfo createPlaceInfo(final GeoServiceItem item) {
        final GeoServiceGeocodingResult result = item.getResult();
        if (result == null || result.getGeometry() == null) {
            return null;
        }
        final FeatureCollection featureCollection = result.getGeometry();
        final List<Feature> features = featureCollection.getFeatures();
        if (features == null || features.isEmpty()) {
            return null;
        }

        final Point locationPoint = firstPoint(features);
        if (locationPoint == null) {
            log.debug("No location point feature for place: {}", item.getPlaceName());
            return null;
        }
        final LngLatAlt location = locationPoint.getCoordinates();

        final Polygon viewportPolygon = firstPolygon(features);
        final PlaceInfo bounded = buildBoundedPlaceInfo(
                item.getPlaceName(), location, viewportPolygon);
        if (bounded != null) {
            return bounded;
        }
        return new PlaceInfo(item.getPlaceName(), location.getLatitude(),
                location.getLongitude());
    }

    private PlaceInfo buildBoundedPlaceInfo(final String placeName,
            final LngLatAlt location, final Polygon viewportPolygon) {
        if (viewportPolygon == null) {
            return null;
        }
        final List<List<LngLatAlt>> viewportRings = viewportPolygon.getCoordinates();
        if (viewportRings == null || viewportRings.isEmpty()) {
            return null;
        }
        final List<LngLatAlt> viewportOutline = viewportRings.get(0);
        if (viewportOutline == null || viewportOutline.size() <= 2) {
            return null;
        }
        return new PlaceInfo(placeName, location,
                viewportOutline.get(0), viewportOutline.get(2));
    }

    private Point firstPoint(final List<Feature> features) {
        for (final Feature feature : features) {
            if (feature != null && feature.getGeometry() instanceof Point point) {
                return point;
            }
        }
        return null;
    }

    private Polygon firstPolygon(final List<Feature> features) {
        for (final Feature feature : features) {
            if (feature != null && feature.getGeometry() instanceof Polygon polygon) {
                return polygon;
            }
        }
        return null;
    }

    /**
     * @return whether the current person is hidden because living.
     */
    private boolean isHiddenLiving() {
        return !renderingContext.isUser() && le.estimate();
    }

    /**
     * @return whether the current person is hidden because confidential.
     */
    private boolean isHiddenConfidential() {
        if (renderingContext.isAdmin()) {
            return false;
        }
        final PersonVisitor visitor = new PersonVisitor();
        person.accept(visitor);
        return visitor.isConfidential();
    }
}
