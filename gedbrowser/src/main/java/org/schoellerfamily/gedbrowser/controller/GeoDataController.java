package org.schoellerfamily.gedbrowser.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.renderer.PlaceListRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.keys.KeyManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@Slf4j
public abstract class GeoDataController extends DatedDataController {

    /** */
    @Autowired
    private transient GeoServiceClient client;

    /** */
    @Autowired
    private transient KeyManager keyManager;

    /**
     * @param person the person we are displaying
     * @param renderingContext the current rendering context
     * @return the list of places for the person
     */
    protected final List<PlaceInfo> fetchPlaces(final Person person,
            final RenderingContext renderingContext) {
        final PlaceListRenderer pl = new PlaceListRenderer(person, client,
                renderingContext);
        final List<PlaceInfo> places = pl.render();
        logPlaces(places);
        return places;
    }

    /**
     * Dump the places to debug log.
     *
     * @param places the places
     */
    private void logPlaces(final List<PlaceInfo> places) {
        if (log.isDebugEnabled()) {
            for (final PlaceInfo place : places) {
                log.debug(place.toString());
            }
        }
    }

    /**
     * @return the key string for talking to the mapping services
     */
    protected final String getMapsKey() {
        return keyManager.getMapsKey();
    }
}
