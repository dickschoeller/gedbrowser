package org.schoellerfamily.gedbrowser.persistence.repository;

import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dick Schoeller
 */
public interface SourceDocumentRepository extends
    CrudRepository<SourceDocument, String>,
    FindableDocument<Source, SourceDocument>  {
}
