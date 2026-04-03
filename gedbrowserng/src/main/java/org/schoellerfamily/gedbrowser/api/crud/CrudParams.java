package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * Represents crud params.
 *
 * @author Richard Schoeller
 */
public class CrudParams {
    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    /**
     * Executes crud params.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    public CrudParams(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        this.loader = loader;
        this.toDocConverter = toDocConverter;
        this.repositoryManager = repositoryManager;
    }

    /**
     * Get the class that loads a file into the database if not already there.
     *
     * @return the loader
     */
    public final GedObjectFileLoader getLoader() {
        return loader;
    }

    /**
     * Get the class that converts from GedObject to GedDocument.
     *
     * @return the converter
     */
    public final GedObjectToGedDocumentMongoConverter getConverter() {
        return toDocConverter;
    }

    /**
     * Gets the repository manager.
     *
     * @return the repository manager
     */
    public final RepositoryManagerMongo getRepositoryManager() {
        return repositoryManager;
    }
}
