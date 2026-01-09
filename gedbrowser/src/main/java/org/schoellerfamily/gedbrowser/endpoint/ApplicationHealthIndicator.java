package org.schoellerfamily.gedbrowser.endpoint;

import java.util.List;
import java.util.Map;

import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.boot.health.contributor.Health.Builder;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Add our own information to the health indicator.
 *
 * @author Dick Schoeller
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
