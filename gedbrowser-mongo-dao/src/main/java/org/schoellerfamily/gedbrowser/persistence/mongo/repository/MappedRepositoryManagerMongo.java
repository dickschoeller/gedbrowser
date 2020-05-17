package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Implements getting the map for the various mapped repository managers.
 *
 * @author Dick Schoeller
 */
public abstract class MappedRepositoryManagerMongo implements HasRepoMap {
    /**
     * This map manages the relationship between GedObject classes and
     * repositories.
     */
    private final Map<Class<? extends GedObject>, Object>
        classToRepoMap = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public final Map<Class<? extends GedObject>, Object> getMap() {
        return classToRepoMap;
    }
}
