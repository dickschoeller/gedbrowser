package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides submitter repo values to calling code.
 *
 * @author Richard Schoeller
 */
public interface SubmitterRepoProvider extends HasRepoMap {
    /**
     * Sets the submitter document repository.
     *
     * @param repository the repository
     */
    @Autowired
    default void setSubmitterDocumentRepository(
            final SubmitterDocumentRepositoryMongo repository) {
        getMap().put(Submitter.class, repository);
    }

    /**
     * Gets the submitter document repository.
     *
     * @return the repository
     */
    default SubmitterDocumentRepositoryMongo getSubmitterDocumentRepository() {
        return (SubmitterDocumentRepositoryMongo) getMap().get(Submitter.class);
    }
}
