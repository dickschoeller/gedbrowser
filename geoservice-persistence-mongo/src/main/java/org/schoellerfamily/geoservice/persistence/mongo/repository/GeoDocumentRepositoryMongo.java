package org.schoellerfamily.geoservice.persistence.mongo.repository;

import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.repository.GeocodableDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dick Schoeller
 */
public interface GeoDocumentRepositoryMongo
        extends CrudRepository<GeoDocumentMongo, String>,
        GeocodableDocument {
}
