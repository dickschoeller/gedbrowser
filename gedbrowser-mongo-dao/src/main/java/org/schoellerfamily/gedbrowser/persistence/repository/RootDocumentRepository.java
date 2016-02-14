package org.schoellerfamily.gedbrowser.persistence.repository;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dick Schoeller
 */
public interface RootDocumentRepository extends
    CrudRepository<RootDocument, String>,
        FindableDocument<Root, RootDocument> {
}
