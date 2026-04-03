package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Provides access to the repository map used by mapped repository managers.
 *
 * @author Richard Schoeller
 */
public interface HasRepoMap {
    /**
     * Provide the map to the derived class.
     *
     * @return the maps of classes to repository objects
     */
    Map<Class<? extends GedObject>, Object> getMap();
}
