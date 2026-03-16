package org.schoellerfamily.gedbrowser.api.endpoint;

import java.util.List;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Actuator endpoint to log duplicate dataset documents without deleting them.
 */
@Component
@Endpoint(id = "logduplicates")
@RequiredArgsConstructor
@Slf4j
public class LogDuplicatesEndpoint {
    /** */
    private final DuplicateCleanupService duplicateCleanupService;

    /**
     * Executes invoke.
     *
     * @return the resulting list
     */
    @ReadOperation
    public List<String> invoke() {
        log.info("Invoke logduplicates");
        final Map<String, Integer> duplicates = duplicateCleanupService.logDuplicates();
        return List.of(
            "Logged duplicates: roots=%d, persons=%d, sources=%d, submitters=%d, total=%d"
                .formatted(
                    duplicates.getOrDefault("roots", 0),
                    duplicates.getOrDefault("persons", 0),
                    duplicates.getOrDefault("sources", 0),
                    duplicates.getOrDefault("submitters", 0),
                    duplicates.getOrDefault("total", 0)
                )
        );
    }
}
