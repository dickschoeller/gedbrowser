package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides person repo values to calling code.
 *
 * @author Richard Schoeller
 */
public interface PersonRepoProvider extends HasRepoMap {
    /**
     * @param repository the repository
     */
    @Autowired
    default void setPersonDocumentRepository(final PersonDocumentRepositoryMongo repository) {
        getMap().put(Person.class, repository);
    }

    /**
     * @return the repository
     */
    default PersonDocumentRepositoryMongo getPersonDocumentRepository() {
        return (PersonDocumentRepositoryMongo) getMap().get(Person.class);
    }
}
