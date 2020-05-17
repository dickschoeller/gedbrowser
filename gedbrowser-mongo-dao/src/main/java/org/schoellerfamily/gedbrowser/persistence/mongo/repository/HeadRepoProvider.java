package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implements a mixin that provides the head repository.
 *
 * @author Dick Schoeller
 */
public interface HeadRepoProvider {
    /**
     * Provide the map to the derived class.
     *
     * @return the maps of classes to repository objects
     */
    Map<Class<? extends GedObject>, Object> getMap();

    /**
     * @param repository the repository
     */
    @Autowired
    default void setHeadDocumentRepository(final HeadDocumentRepositoryMongo repository) {
        getMap().put(Head.class, repository);
    }

    /**
     * @return the repository
     */
    default HeadDocumentRepositoryMongo getHeadDocumentRepository() {
        return (HeadDocumentRepositoryMongo) getMap().get(Head.class);
    }
}
