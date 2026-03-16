package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

import lombok.RequiredArgsConstructor;



/**
 * Handles requests related to crud invoker.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@RequiredArgsConstructor
public abstract class CrudInvoker {
    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    /**
     * Gets the loader.
     *
     * @return the loader
     */
    protected GedObjectFileLoader getLoader() {
        return loader;
    }

    /**
     * Gets the converter.
     *
     * @return the converter
     */
    protected GedObjectToGedDocumentMongoConverter getConverter() {
        return toDocConverter;
    }

    /**
     * Gets the manager.
     *
     * @return the manager
     */
    protected RepositoryManagerMongo getManager() {
        return repositoryManager;
    }
}
