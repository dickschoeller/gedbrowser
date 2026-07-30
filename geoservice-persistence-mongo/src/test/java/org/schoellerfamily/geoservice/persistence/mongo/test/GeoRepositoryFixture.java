package org.schoellerfamily.geoservice.persistence.mongo.test;

import org.schoellerfamily.geoservice.persistence.fixture.GeoCodeTestFixture;
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;

import com.mongodb.client.MongoDatabase;

/**
 * Provides fixture data for geo repository tests.
 *
 * @author Richard Schoeller
 */
public final class GeoRepositoryFixture extends GeoCodeTestFixture {
    /** */
    private final GeoDocumentRepositoryMongo geoDocumentRepository;

    /** */
    private final MongoDatabase mongoDatabase;

    /**
     * Creates a new GeoRepositoryFixture.
     *
     * @param geoDocumentRepository the geo document repository
      * @param mongoDatabase the mongo database
     */
    public GeoRepositoryFixture(final GeoDocumentRepositoryMongo geoDocumentRepository,
          final MongoDatabase mongoDatabase) {
        super();
        this.geoDocumentRepository = geoDocumentRepository;
          this.mongoDatabase = mongoDatabase;
    }

    /**
     * Clear and reload all of the tables in the repository.
     */
    public void loadRepository() {
        geoDocumentRepository.deleteAll();
        // TODO put in a loader
    }

    /**
     * Clear out all of the tables in the repository.
     */
    public void clearRepository() {
        geoDocumentRepository.deleteAll();
        mongoDatabase.drop();
    }
}
