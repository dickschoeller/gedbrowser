package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides source repo values to calling code.
 *
 * @author Richard Schoeller
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
