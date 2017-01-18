package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.model.GeoServiceGeocodingResult;
import org.schoellerfamily.geoservice.model.GeoServiceItem;

/**
 * @author Dick Schoeller
 */
public final class PlaceListRenderer {
    /** */
    private final Person person;
    /** */
    private final GeoServiceClient client;

    /**
     * @param person the person
     * @param client the geoservice client
     */
    public PlaceListRenderer(final Person person,
            final GeoServiceClient client) {
        this.person = person;
        this.client = client;
    }

    /**
     * @return the list of place information
     */
    public List<PlaceInfo> render() {
        final Set<String> places = new TreeSet<>();
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
}
