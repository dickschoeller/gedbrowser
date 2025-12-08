package org.schoellerfamily.gedbrowser.endpoint;

import java.util.List;
import java.util.Map;

import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Add our own information to the health indicator.
 *
 * @author Dick Schoeller
 */
@Component
@Slf4j
public class ApplicationHealthIndicator implements HealthIndicator {

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    @Autowired
    private transient GedObjectFileLoader loader;

    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public final Health health() {
        log.debug("Health");
        final Builder builder = Health.up();
        log.debug("    " + appInfo.getVersion());
        builder.withDetail("version", appInfo.getVersion());
        final List<Map<String, Object>> details = loader.details(repositoryManager);
        log.debug("    " + details.size() + " datasets");
        builder.withDetail("datasets", details);
        builder.up();
        return builder.build();
    }
}
