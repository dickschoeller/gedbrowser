package org.schoellerfamily.gedbrowser.persistence.repository;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dick Schoeller
 */
public interface FamilyDocumentRepository extends
    CrudRepository<FamilyDocument, String>,
        FindableDocument<Family, FamilyDocument> {
}
