package org.schoellerfamily.gedbrowser.api.endpoint;

import java.util.List;
import java.util.Map;

import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.Health.Builder;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Reports status information for application health.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationHealthIndicator implements HealthIndicator {
    /** */
    private final ApplicationInfo appInfo;

    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    /**
     * Executes health.
     *
     * @return the resulting health
     */
    @Override
    public final Health health() {
        log.debug("Health");
        final Builder builder = Health.up();
        log.debug("    {}", appInfo.getVersion());
        builder.withDetail("version", appInfo.getVersion());
        final List<Map<String, Object>> details = loader.details(repositoryManager);
        log.debug("    {} datasets", details.size());
        builder.withDetail("datasets", details);
        builder.up();
        return builder.build();
    }
}
