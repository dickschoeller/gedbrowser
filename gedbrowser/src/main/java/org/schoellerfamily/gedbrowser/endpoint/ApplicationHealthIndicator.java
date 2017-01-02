package org.schoellerfamily.gedbrowser.endpoint;

import org.schoellerfamily.gedbrowser.controller.ApplicationInfo;
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
    private transient ApplicationInfo appInfo;

    /**
     * {@inheritDoc}
     */
    @Override
    public Health health() {
        final Builder upBuilder = Health.up();
        upBuilder.withDetail("version", appInfo.getVersion());
        // TODO add more stuff about databases here.
        return upBuilder.build();
    }
}
