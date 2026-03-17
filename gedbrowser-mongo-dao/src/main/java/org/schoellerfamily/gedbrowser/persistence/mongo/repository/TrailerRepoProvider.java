package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides trailer repo values to calling code.
 *
 * @author Richard Schoeller
 */
public interface TrailerRepoProvider extends HasRepoMap {
    /**
     * Sets the trailer document repository.
     *
     * @param repository the repository
     */
    @Autowired
    default void setTrailerDocumentRepository(final TrailerDocumentRepositoryMongo repository) {
        getMap().put(Trailer.class, repository);
    }

    /**
     * Gets the trailer document repository.
     *
     * @return the repository
     */
    default TrailerDocumentRepositoryMongo getTrailerDocumentRepository() {
        return (TrailerDocumentRepositoryMongo) getMap().get(Trailer.class);
    }
}
