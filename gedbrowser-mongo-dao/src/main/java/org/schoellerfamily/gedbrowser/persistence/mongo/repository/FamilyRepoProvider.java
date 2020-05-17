package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implements a mixin that provides the family repository.
 *
 * @author Dick Schoeller
 */
public interface FamilyRepoProvider extends HasRepoMap {
    /**
     * @param repository the repository
     */
    @Autowired
    default void setFamilyDocumentRepository(final FamilyDocumentRepositoryMongo repository) {
        getMap().put(Family.class, repository);
    }

    /**
     * @return the repository
     */
    default FamilyDocumentRepositoryMongo getFamilyDocumentRepository() {
        return (FamilyDocumentRepositoryMongo) getMap().get(Family.class);
    }

}
