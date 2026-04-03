package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * Defines persistence operations for root document repository mongo.
 *
 * @author Richard Schoeller
 */
public interface RootDocumentRepositoryMongo extends
    CrudRepository<RootDocumentMongo, String>,
        FindableDocument<Root, RootDocument> {
}
