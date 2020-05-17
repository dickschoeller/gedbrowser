package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implements a mixin that provides the person repository.
 *
 * @author Dick Schoeller
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
