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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;



/**
 * Bootstraps the application.
 *
 * @author Richard Schoeller
 */
@ComponentScan(basePackages = { "org.schoellerfamily.geoservice" })
@EnableAutoConfiguration
@Configuration
@RequiredArgsConstructor
public class Application {
    /** */
    @Value("${geoservice.keyfile:/var/lib/gedbrowser/google-geocoding-key}")
    private final String keyfile;

    /** */
    @Value("${geoservice.geocoder:google}")
    private final String geocoder;

    /** */
    private GeoCode gcc;

    /** */
    private GeoCoder geoCoder;

    /** */
    private final GeoDocumentRepositoryMongo repositoryMongo;

    /**
     * Starts the application.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Creates and configures the geo code bean.
     *
     * @return the configured geo code bean
     */
    @Bean
    public GeoCode persistenceManager() {
        if (gcc == null) {
            gcc = new GeoCodeMongo(geoCoder(), repositoryMongo);
        }
        return gcc;
    }

    /**
     * Creates and configures the application info bean.
     *
     * @return the configured application info bean
     */
    @Bean
    public ApplicationInfo appInfo() {
        return new ApplicationInfo();
    }

    /**
     * Creates and configures the geo code loader bean.
     *
     * @return the configured geo code loader bean
     */
    @Bean
    public GeoCodeLoader loader() {
        return new GeoCodeLoader(persistenceManager());
    }

    /**
     * Creates and configures the key manager bean.
     *
     * @return the configured key manager bean
     */
    @Bean
    public KeyManager keyManager() {
        if ("stub".equals(keyfile)) {
            return new KeyManagerStub();
        }
        return new KeyManagerImpl(keyfile);
    }

    /**
     * Creates and configures the geo coder bean.
     *
     * @return the configured geo coder bean
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
