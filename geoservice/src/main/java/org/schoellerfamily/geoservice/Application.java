package org.schoellerfamily.geoservice;

import org.schoellerfamily.geoservice.backup.GeoCodeBackup;
import org.schoellerfamily.geoservice.controller.ApplicationInfo;
import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.geocoder.GoogleGeoCoder;
import org.schoellerfamily.geoservice.geocoder.StubGeoCoder;
import org.schoellerfamily.geoservice.keys.KeyManager;
import org.schoellerfamily.geoservice.keys.KeyManagerImpl;
import org.schoellerfamily.geoservice.keys.KeyManagerStub;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import org.schoellerfamily.geoservice.persistence.mongo.GeoCodeMongo;
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
    @Bean
    public GeoCode persistenceManager() {
        return new GeoCodeMongo();
    }

    /**
     * @return the backup manager
     */
    @Bean
    public GeoCodeBackup backupManager() {
        return new GeoCodeBackup();
    }

    /**
     * @return the backup manager
     */
    @Bean
    public ApplicationInfo appInfo() {
        return new ApplicationInfo();
    }

    /**
     * @return the geocodeloader
     */
    @Bean
    public GeoCodeLoader loader() {
        return new GeoCodeLoader();
    }

    /**
     * @return the manager of google keys
     */
    @Bean
    public KeyManager keyManager() {
        if ("stub".equals(keyfile)) {
            return new KeyManagerStub();
        }
        return new KeyManagerImpl(keyfile);
    }

    /**
     * @return the geocoder
     */
    @Bean
    public GeoCoder geoCoder() {
        if ("stub".equals(keyfile)) {
            return new StubGeoCoder(new String[0]);
        }
        final String key = keyManager().getGeocodingKey();
        return new GoogleGeoCoder(key);
    }
}
