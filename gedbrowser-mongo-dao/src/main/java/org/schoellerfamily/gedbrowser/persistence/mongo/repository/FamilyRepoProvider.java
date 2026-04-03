package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides family repo values to calling code.
 *
 * @author Richard Schoeller
 */
public interface FamilyRepoProvider extends HasRepoMap {
    /**
     * Sets the family document repository.
     *
     * @param repository the repository
     */
    @Autowired
    default void setFamilyDocumentRepository(final FamilyDocumentRepositoryMongo repository) {
        getMap().put(Family.class, repository);
    }

    /**
     * Gets the family document repository.
     *
     * @return the repository
     */
    default FamilyDocumentRepositoryMongo getFamilyDocumentRepository() {
        return (FamilyDocumentRepositoryMongo) getMap().get(Family.class);
    }

}
