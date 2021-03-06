package org.schoellerfamily.geoservice.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.geoservice.controller.ApplicationInfo;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Add our own information to the health indicator.
 *
 * @author Dick Schoeller
 */
@Component
public class ApplicationHealthIndicator implements HealthIndicator {
    /** */
    @Autowired
    private GeoCode gcc;

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /**
     * {@inheritDoc}
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
