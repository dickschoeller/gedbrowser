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

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

/**
 * Factory for geoservice beans.
 */
@Factory
public class GeoServiceFactory {
    /** */
    @Value("${geoservice.keyfile:/var/lib/gedbrowser/google-geocoding-key}")
    private String keyfile;

    /** */
    private GeoCode gcc;

    /** */
    private GeoCoder geoCoder;

    /** */
    private final GeoDocumentRepositoryMongo repositoryMongo;

    /**
     * Create a new bean factory.
     *
     * @param repositoryMongo repository backing store
     */
    public GeoServiceFactory(final GeoDocumentRepositoryMongo repositoryMongo) {
        this.repositoryMongo = repositoryMongo;
    }

    /**
     * Creates and configures the geo code bean.
     *
     * @return the configured geo code bean
     */
    @Singleton
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
    @Singleton
    public ApplicationInfo appInfo() {
        return new ApplicationInfo();
    }

    /**
     * Creates and configures the geo code loader bean.
     *
     * @return the configured geo code loader bean
     */
    @Singleton
    public GeoCodeLoader loader() {
        return new GeoCodeLoader(persistenceManager());
    }

    /**
     * Creates and configures the key manager bean.
     *
     * @return the configured key manager bean
     */
    @Singleton
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
    @Singleton
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
