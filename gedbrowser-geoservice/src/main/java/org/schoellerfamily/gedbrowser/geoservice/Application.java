package org.schoellerfamily.gedbrowser.geoservice;

import org.schoellerfamily.gedbrowser.geocode.dao.GeoCodeDao;
import org.schoellerfamily.gedbrowser.geocodecache.GeoCodeCache;
import org.schoellerfamily.gedbrowser.geoservice.backup.GeoCodeBackup;
import org.schoellerfamily.gedbrowser.geoservice.controller.ApplicationInfo;
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
        return new GeoCodeCache();
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
