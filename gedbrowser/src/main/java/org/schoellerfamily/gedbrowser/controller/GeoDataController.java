package org.schoellerfamily.gedbrowser.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.renderer.PlaceListRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.keys.KeyManager;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@Slf4j
public abstract class GeoDataController extends DatedDataController {
    private final GeoServiceClient client;

    private final KeyManager keyManager;

    /**
     * All arguments constructor.
     *
     * @param appInfo the application info
     * @param users info about the known application users
     * @param loader enable loading gedcom files
     * @param provider enable calendar processing
     * @param repositoryManager enable data storage
     * @param client enable interaction with geoservice
     * @param keyManager enable interacting with google
     */
    public GeoDataController(final ApplicationInfo appInfo,
            final Users<? extends User> users,
            final GedObjectFileLoader loader,
            final CalendarProvider provider,
            final RepositoryManagerMongo repositoryManager,
            final GeoServiceClient client,
            final KeyManager keyManager) {
        super(appInfo, users, loader, provider, repositoryManager);
        this.client = client;
        this.keyManager = keyManager;
    }

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
