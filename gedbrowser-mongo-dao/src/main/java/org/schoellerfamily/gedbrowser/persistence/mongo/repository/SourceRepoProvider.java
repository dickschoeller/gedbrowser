package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implements a mixin that provides the source repository.
 *
 * @author Dick Schoeller
 */
public interface SourceRepoProvider extends HasRepoMap {
    /**
     * @param repository the repository
     */
    @Autowired
    default void setSourceDocumentRepository(final SourceDocumentRepositoryMongo repository) {
        getMap().put(Source.class, repository);
    }

    /**
     * @return the repository
     */
    default SourceDocumentRepositoryMongo getSourceDocumentRepository() {
        return (SourceDocumentRepositoryMongo) getMap().get(Source.class);
    }
}
