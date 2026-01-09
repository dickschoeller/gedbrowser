package org.schoellerfamily.geoservice;

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
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main module of a Spring Boot application to provide cached geocode lookups
 * for GedBrowser.
 *
 * @author Dick Schoeller
 */
@ComponentScan(basePackages = { "org.schoellerfamily.geoservice" })
@EnableAutoConfiguration
public class Application {
    /** */
    @Value("${geoservice.keyfile:/var/lib/gedbrowser/google-geocoding-key}")
    private String keyfile;

    /** */
    private GeoCode gcc;

    /** */
    private GeoCoder geoCoder;

    /** */
    @Autowired
    private GeoDocumentRepositoryMongo repositoryMongo;

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
        if (gcc == null) {
            gcc = new GeoCodeMongo(geoCoder(), repositoryMongo);
        }
        return gcc;
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
        return new GeoCodeLoader(persistenceManager());
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
        if (geoCoder != null) {
            return geoCoder;
        }
        if ("stub".equals(keyfile)) {
            geoCoder = new StubGeoCoder(new String[0]);
            return geoCoder;
        }
        final String key = keyManager().getGeocodingKey();
        geoCoder = new GoogleGeoCoder(key);
        return geoCoder;
    }
}
