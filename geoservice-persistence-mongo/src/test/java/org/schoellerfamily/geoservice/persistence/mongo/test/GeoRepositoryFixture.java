package org.schoellerfamily.geoservice.persistence.mongo.test;

import java.io.IOException;

import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
public final class GeoRepositoryFixture {
    /** */
    @Autowired
    private transient GeoDocumentRepositoryMongo geoDocumentRepository;

    /**
     * This is private because this is a singleton.
     */
    public GeoRepositoryFixture() {
        // Empty constructor.
    }

    /**
     * Clear and reload all of the tables in the repository.
     *
     * @throws IOException if there is a problem reading the file
     */
    public void loadRepository() throws IOException {
        clearRepository();
        // TODO put in a loader
    }

    /**
     * Clear out all of the tables in the repository.
     */
    public void clearRepository() {
        geoDocumentRepository.deleteAll();
    }
}
