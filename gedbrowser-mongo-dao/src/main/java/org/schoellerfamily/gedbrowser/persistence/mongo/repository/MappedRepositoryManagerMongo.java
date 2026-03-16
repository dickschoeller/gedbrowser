package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Represents mapped repository manager mongo for persistence operations.
 *
 * @author Richard Schoeller
 */
public abstract class MappedRepositoryManagerMongo implements HasRepoMap {
    /**
     * Creates a new MappedRepositoryManagerMongo.
     */
    public MappedRepositoryManagerMongo() {
    }

    /**
     * This map manages the relationship between GedObject classes and their corresponding repositories.
     */
    private final Map<Class<? extends GedObject>, Object>
        classToRepoMap = new HashMap<>();

    /**
     * Gets the map.
     *
     * @return the map
     */
    @Override
    public final Map<Class<? extends GedObject>, Object> getMap() {
        return classToRepoMap;
    }
}
