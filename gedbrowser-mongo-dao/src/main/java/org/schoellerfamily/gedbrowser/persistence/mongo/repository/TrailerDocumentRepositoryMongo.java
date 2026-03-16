package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    TrailerDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * Defines persistence operations for trailer document repository mongo.
 *
 * @author Richard Schoeller
 */
public interface TrailerDocumentRepositoryMongo extends
    CrudRepository<TrailerDocumentMongo, String>,
        FindableDocument<Trailer, TrailerDocument> {
}
