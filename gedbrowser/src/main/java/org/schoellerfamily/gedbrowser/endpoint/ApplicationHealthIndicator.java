package org.schoellerfamily.gedbrowser.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.controller.ApplicationInfo;
import org.schoellerfamily.gedbrowser.loader.GedFileLoader;
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
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    @Autowired
    private transient GedFileLoader loader;

    /**
     * {@inheritDoc}
     */
    @Override
    public Health health() {
        logger.info("Add to health report");
        final Builder builder = Health.up();
        builder.withDetail("version", appInfo.getVersion());
        builder.withDetail("datasets", loader.details());
        builder.up();
        return builder.build();
    }
}
