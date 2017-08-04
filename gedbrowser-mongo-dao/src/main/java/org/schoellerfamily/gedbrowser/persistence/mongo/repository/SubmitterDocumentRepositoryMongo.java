package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    SubmitterDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dick Schoeller
 */
public interface SubmitterDocumentRepositoryMongo extends
    CrudRepository<SubmitterDocumentMongo, String>,
        FindableDocument<Submitter, SubmitterDocument> {
}
