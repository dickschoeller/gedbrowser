package org.schoellerfamily.gedbrowser.api.endpoint;

import java.util.List;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Exposes operations for the cleanup duplicates endpoint.
 */
@Component
@Endpoint(id = "cleanupduplicates")
@RequiredArgsConstructor
@Slf4j
public class CleanupDuplicatesEndpoint {
    /** */
    private final DuplicateCleanupService duplicateCleanupService;

    /**
     * Executes invoke.
     *
     * @return the resulting list
     */
    @ReadOperation
    public List<String> invoke() {
        log.info("Invoke cleanupduplicates");
        final Map<String, Integer> deleted = duplicateCleanupService.cleanup();
        return List.of(
            "Removed duplicates: roots=%d, persons=%d, sources=%d, submitters=%d, total=%d"
                .formatted(
                    deleted.getOrDefault("roots", 0),
                    deleted.getOrDefault("persons", 0),
                    deleted.getOrDefault("sources", 0),
                    deleted.getOrDefault("submitters", 0),
                    deleted.getOrDefault("total", 0)
                )
        );
    }
}
