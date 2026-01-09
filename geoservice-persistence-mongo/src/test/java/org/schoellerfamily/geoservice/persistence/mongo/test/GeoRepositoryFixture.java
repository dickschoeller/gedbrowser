package org.schoellerfamily.geoservice.persistence.mongo.test;

import java.io.IOException;

import org.schoellerfamily.geoservice.persistence.fixture.GeoCodeTestFixture;
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Test fixture for the MongoDB tests. Extends the standard fixture a little.
 *
 * @author Dick Schoeller
 */
public final class GeoRepositoryFixture extends GeoCodeTestFixture {
    /** */
    private final GeoDocumentRepositoryMongo geoDocumentRepository;

    /** */
    private final MongoTemplate mongoTemplate;

    /**
     * Constructor.
     *
     * @param geoDocumentRepository a MongoDB repository for geo documents
     * @param mongoTemplate a MongoDB template
     */
    public GeoRepositoryFixture(final GeoDocumentRepositoryMongo geoDocumentRepository,
        final MongoTemplate mongoTemplate) {
        super();
        this.geoDocumentRepository = geoDocumentRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Clear and reload all of the tables in the repository.
     *
     * @throws IOException if there is a problem reading the file
     */
    public void loadRepository() throws IOException {
        geoDocumentRepository.deleteAll();
        // TODO put in a loader
    }

    /**
     * Clear out all of the tables in the repository.
     */
    public void clearRepository() {
        geoDocumentRepository.deleteAll();
        mongoTemplate.getDb().drop();
    }
}
