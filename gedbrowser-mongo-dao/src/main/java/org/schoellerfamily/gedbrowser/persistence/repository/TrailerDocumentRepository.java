package org.schoellerfamily.gedbrowser.persistence.repository;

import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dick Schoeller
 */
public interface TrailerDocumentRepository extends
    CrudRepository<TrailerDocument, String>,
        FindableDocument<Trailer, TrailerDocument> {
}
