package org.schoellerfamily.geoservice;

import org.schoellerfamily.gedservice.persistence.stub.GeoCodeCache;
import org.schoellerfamily.geoservice.backup.GeoCodeBackup;
import org.schoellerfamily.geoservice.controller.ApplicationInfo;
import org.schoellerfamily.geoservice.keys.KeyManager;
import org.schoellerfamily.geoservice.persistence.GeoCodeDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main module of a Spring Boot application to provide cached geocode lookups
 * for GedBrowser.
 *
 * @author Dick Schoeller
 */
@SpringBootApplication
public class Application {
    /** */
    @Value("${geoservice.keyfile:/var/lib/gedbrowser/google-geocoding-key}")
    private transient String keyfile;

    /**
     * @param args usual command line arguments are handled by Spring Boot.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * @return the persistence manager
     */
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
    @Bean
    public GeoCodeDao persistenceManager() {
        // CHECKSTYLE:ON
        final KeyManager km = new KeyManager();
        final String key = km.readKeyFile(keyfile);
        return new GeoCodeCache(key);
    }

    /**
     * @return the backup manager
     */
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
    @Bean
    public GeoCodeBackup backupManager() {
        // CHECKSTYLE:ON
        return new GeoCodeBackup();
    }

    /**
     * @return the backup manager
     */
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
    @Bean
    public ApplicationInfo appInfo() {
        // CHECKSTYLE:ON
        return new ApplicationInfo();
    }
}
