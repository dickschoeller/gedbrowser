package org.schoellerfamily.geoservice.persistence.mongo.repository;

import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.repository.GeocodableDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * Defines persistence operations for geo document repository mongo.
 *
 * @author Richard Schoeller
 */
public interface GeoDocumentRepositoryMongo
        extends CrudRepository<GeoDocumentMongo, String>,
        GeocodableDocument {
}
