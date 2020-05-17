package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implements a mixin that provides the trailer repository.
 *
 * @author Dick Schoeller
 */
public interface TrailerRepoProvider extends HasRepoMap {
    /**
     * @param repository the repository
     */
    @Autowired
    default void setTrailerDocumentRepository(final TrailerDocumentRepositoryMongo repository) {
        getMap().put(Trailer.class, repository);
    }

    /**
     * @return the repository
     */
    default TrailerDocumentRepositoryMongo getTrailerDocumentRepository() {
        return (TrailerDocumentRepositoryMongo) getMap().get(Trailer.class);
    }
}
