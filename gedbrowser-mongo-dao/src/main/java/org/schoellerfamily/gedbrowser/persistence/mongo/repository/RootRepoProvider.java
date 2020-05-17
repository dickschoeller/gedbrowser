package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implements a mixin that provides the root repository.
 *
 * @author Dick Schoeller
 */
public interface RootRepoProvider extends HasRepoMap {
    /**
     * @param repository the repository
     */
    @Autowired
    default void setRootDocumentRepository(final RootDocumentRepositoryMongo repository) {
        getMap().put(Root.class, repository);
    }

    /**
     * @return the repository
     */
    default RootDocumentRepositoryMongo getRootDocumentRepository() {
        return (RootDocumentRepositoryMongo) getMap().get(Root.class);
    }
}
