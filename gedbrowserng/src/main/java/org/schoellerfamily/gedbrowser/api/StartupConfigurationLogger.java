package org.schoellerfamily.gedbrowser.api;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Logs key runtime configuration when the application starts.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public final class StartupConfigurationLogger {
    /** */
    private final Environment environment;

    /** */
    @Value("${geoservice.keyfile:/var/lib/gedbrowser/google-geocoding-key}")
    private String geoserviceKeyfile;

    /** */
    @Value("${geoservice.port:8082}")
    private String geoservicePort;

    /**
     * Emit a single startup log line describing geo service related runtime config.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void logStartupConfiguration() {
        final String activeProfiles = environment.getActiveProfiles().length == 0
                ? "default"
                : Arrays.toString(environment.getActiveProfiles());
        final String geocoderMode = "stub".equalsIgnoreCase(geoserviceKeyfile) ? "stub" : "file";
        log.info("Startup config: profiles={}, geoservice.port={}, geoservice.keyfile={}, geocoderMode={}",
                activeProfiles, geoservicePort, geoserviceKeyfile, geocoderMode);
    }
}
