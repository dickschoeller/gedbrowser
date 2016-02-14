package org.schoellerfamily.gedbrowser.persistence.repository;

import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dick Schoeller
 */
public interface PersonDocumentRepository extends
        CrudRepository<PersonDocument, String>,
        FindableByNameDocument<Person, PersonDocument> {
}
