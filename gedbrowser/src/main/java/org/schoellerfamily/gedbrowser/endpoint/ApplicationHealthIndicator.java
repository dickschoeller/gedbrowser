package org.schoellerfamily.gedbrowser.endpoint;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.loader.GedFileLoader;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
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
    public final Health health() {
        logger.debug("Health");
        final Builder builder = Health.up();
        logger.debug("    " + appInfo.getVersion());
        builder.withDetail("version", appInfo.getVersion());
        final List<Map<String, Object>> details = loader.details();
        logger.debug("    " + details.size() + " datasets");
        builder.withDetail("datasets", details);
        builder.up();
        return builder.build();
    }
}
