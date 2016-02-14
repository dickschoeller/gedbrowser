package org.schoellerfamily.gedbrowser.persistence.repository;

import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dick Schoeller
 */
public interface SubmittorDocumentRepository extends
    CrudRepository<SubmittorDocument, String>,
        FindableDocument<Submittor, SubmittorDocument> {
}
