package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implements a mixin that provides the submitter repository.
 *
 * @author Dick Schoeller
 */
public interface SubmitterRepoProvider extends HasRepoMap {
    /**
     * @param repository the repository
     */
    @Autowired
    default void setSubmitterDocumentRepository(
            final SubmitterDocumentRepositoryMongo repository) {
        getMap().put(Submitter.class, repository);
    }

    /**
     * @return the repository
     */
    default SubmitterDocumentRepositoryMongo getSubmitterDocumentRepository() {
        return (SubmitterDocumentRepositoryMongo) getMap().get(Submitter.class);
    }
}
