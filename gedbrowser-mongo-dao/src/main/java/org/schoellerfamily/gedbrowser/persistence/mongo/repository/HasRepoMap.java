package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Describes getting the map for the various repository managers.
 *
 * @author Dick Schoeller
 */
public interface HasRepoMap {
    /**
     * Provide the map to the derived class.
     *
     * @return the maps of classes to repository objects
     */
    Map<Class<? extends GedObject>, Object> getMap();
}
