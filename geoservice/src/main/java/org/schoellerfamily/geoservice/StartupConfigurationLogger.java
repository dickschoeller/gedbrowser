package org.schoellerfamily.geoservice;

import java.util.Set;

import io.micronaut.context.annotation.Value;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import lombok.extern.slf4j.Slf4j;

/**
 * Logs key runtime configuration values when the server starts.
 */
@Singleton
@Slf4j
public class StartupConfigurationLogger {
    /** */
    @Inject
    private GeoCode geoCode;

    /** */
    @Inject
    private GeoCodeLoader loader;

    /** */
    @Value("${geoservice.keyfile:/var/lib/gedbrowser/google-geocoding-key}")
    private String keyfile;

    /** */
    @Value("${geoservice.clear:false}")
    private boolean clearOnStartup;

    /** */
    @Value("${geoservice.loadfile:/var/lib/gedbrowser/geoservice-loadfile.txt}")
    private String loadFile;

    /** */
    @Value("${micronaut.server.port:8082}")
    private String serverPort;

    /** */
    private final Set<String> activeEnvironments;

    /**
     * Constructor.
     *
     * @param activeEnvironments active Micronaut environments
     */
    public StartupConfigurationLogger(final Set<String> activeEnvironments) {
        this.activeEnvironments = activeEnvironments;
    }

    /**
     * Emit one startup line with effective geoservice config details.
     *
     * @param event startup event
     */
    @EventListener
    public void onStartup(final ServerStartupEvent event) {
        final String mode = "stub".equals(keyfile) ? "stub" : "google";
        log.info("Geoservice startup: port={}, environments={}, geoservice.keyfile={}, geocoderMode={}, geoservice.clear={}",
                serverPort, activeEnvironments, keyfile, mode, clearOnStartup);
        if (clearOnStartup) {
            log.warn("geoservice.clear=true. Clearing geocode database and reloading from {}", loadFile);
            geoCode.clear();
            loader.load(loadFile);
            log.info("Startup clear/reload completed. {} locations in cache.", geoCode.size());
        }
    }
}
