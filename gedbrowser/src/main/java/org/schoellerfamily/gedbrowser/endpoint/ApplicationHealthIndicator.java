package org.schoellerfamily.gedbrowser.endpoint;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
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
@Slf4j
@RequiredArgsConstructor
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
        try {
            final List<Map<String, Object>> details =
                Optional.ofNullable(loader.details(repositoryManager)).orElse(List.of());
            log.debug("    {} datasets", details.size());
            builder.withDetail("datasets", details);
        } catch (Exception _) {
            final List<Map<String, Object>> details = List.of();
            log.error("    0 datasets (could not connect to database)");
            builder.withDetail("datasets", details);
        }
        builder.up();
        return builder.build();
    }
}
