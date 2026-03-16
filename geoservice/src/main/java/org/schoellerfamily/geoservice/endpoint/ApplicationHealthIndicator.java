package org.schoellerfamily.geoservice.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.geoservice.controller.ApplicationInfo;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.Health.Builder;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Add our own information to the health indicator.
 *
 * @author Dick Schoeller
 */
@Component
@RequiredArgsConstructor
public class ApplicationHealthIndicator implements HealthIndicator {
    /** */
    private final GeoCode gcc;

    /** */
    private final ApplicationInfo appInfo;
    /**
     * Executes health.
     *
     * @return the resulting health
     */
    @Override
    public final Health health() {
        final Builder upBuilder = Health.up();
        final Map<String, Object> cacheMap = new HashMap<>();
        upBuilder.withDetail("version", appInfo.getVersion());
        cacheMap.put("size", gcc.size());
        cacheMap.put("geocoded", gcc.size() - gcc.countNotFound());
        upBuilder.withDetail("cache", cacheMap);
        return upBuilder.build();
    }
}
