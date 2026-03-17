package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.renderer.PlaceListRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.gedbrowser.security.util.RequestUserUtil;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;



/**
 * Provides services for person geo.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
public class PersonGeoService {

    /** */
    private final GeoServiceClient geoServiceClient;

    /** */
    private final ApplicationInfo appInfo;

    /** */
    private final CalendarProvider provider;

    /**
     * Fetch the places for a person based on the authenticated-user context.
     *
     * @param person the person to fetch places for
     * @param requestUserUtil utility to inspect the current request user
     * @return the list of resolved places
     */
    public List<PlaceInfo> fetchPlaces(
            final Person person, final RequestUserUtil requestUserUtil) {
        final RenderingContext renderingContext = createRenderingContext(requestUserUtil);
        return new PlaceListRenderer(person, geoServiceClient, renderingContext).render();
    }

    private RenderingContext createRenderingContext(
            final RequestUserUtil requestUserUtil) {
        if (requestUserUtil.hasAdmin() || requestUserUtil.hasUser()) {
            return new RenderingContext(requestUserUtil.getUser(), appInfo, provider);
        }
        return RenderingContext.anonymous(appInfo, provider);
    }
}
