package org.schoellerfamily.geoservice.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.geoservice.controller.ApplicationInfo;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

/**
 * Reports status information for application health.
 *
 * @author Richard Schoeller
 */
@Controller("/actuator")
public class ApplicationHealthIndicator {
    /** */
    private final GeoCode gcc;

    /** */
    private final ApplicationInfo appInfo;

    /**
     * Create health endpoint.
     *
     * @param gcc geocode service
     * @param appInfo application info provider
     */
    public ApplicationHealthIndicator(final GeoCode gcc, final ApplicationInfo appInfo) {
        this.gcc = gcc;
        this.appInfo = appInfo;
    }
    /**
     * Executes health.
     *
     * @return the resulting health
     */
    @Get("/health")
    public final Map<String, Object> health() {
        final Map<String, Object> root = new HashMap<>();
        final Map<String, Object> cacheMap = new HashMap<>();
        cacheMap.put("size", gcc.size());
        cacheMap.put("geocoded", gcc.size() - gcc.countNotFound());
        root.put("status", "UP");
        root.put("version", appInfo.getVersion());
        root.put("cache", cacheMap);
        return root;
    }
}
