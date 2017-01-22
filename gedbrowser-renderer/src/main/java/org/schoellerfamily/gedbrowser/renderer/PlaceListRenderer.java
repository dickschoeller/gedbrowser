package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * Generates the collection of places for a provided person.
 *
 * @author Dick Schoeller
 */
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
        this.le = new LivingEstimator(person);
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
        final Collection<String> places = collectPlaceNames();
        return geoCodePlaces(places);
    }

    /**
     * @return the collection of place names associated with this person
     */
    private Collection<String> collectPlaceNames() {
        final Set<String> places = new TreeSet<>();
        // FIXME Feature envy - GedObjects should be able to tell you place.
        for (final GedObject gob : person.getAttributes()) {
            for (final GedObject subgob : gob.getAttributes()) {
                if (subgob instanceof Place) {
                    final Place place = (Place) subgob;
                    final String placeName = place.getString();
                    places.add(placeName);
                }
            }
        }
        // TODO get places from marriages
        return places;
    }

    /**
     * @param places the place to code
     * @return the list of geocode results
     */
    private List<PlaceInfo> geoCodePlaces(final Collection<String> places) {
        final List<PlaceInfo> items = new ArrayList<>(places.size());
        for (final String placeName : places) {
            final GeoServiceItem item = client.get(placeName);
            if (item.getResult() != null) {
                items.add(createPlaceInfo(item));
            }
        }
        return items;
    }

    /**
     * @param item the geoservice item containing the data
     * @return the new place information
     */
    private PlaceInfo createPlaceInfo(final GeoServiceItem item) {
        final GeoServiceGeocodingResult result = item.getResult();
        final FeatureCollection featureCollection = result.getGeometry();
        final List<Feature> features = featureCollection.getFeatures();
        final Feature feature = features.get(0);
        final Point geometry = (Point) feature.getGeometry();
        final LngLatAlt coordinates = geometry.getCoordinates();
        return new PlaceInfo(item.getPlaceName(), coordinates.getLatitude(),
                coordinates.getLongitude());
    }

    /**
     * @return whether the current person is hidden because living.
     */
    private boolean isHiddenLiving() {
        if (renderingContext.isUser()) {
            return false;
        }
        return le.estimate();
    }

    /**
     * @return whether the current person is hidden because confidential.
     */
    private boolean isHiddenConfidential() {
        if (renderingContext.isAdmin()) {
            return false;
        }
        return person.isConfidential();
    }
}