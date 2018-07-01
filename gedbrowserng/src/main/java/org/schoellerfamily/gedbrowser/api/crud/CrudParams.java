package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * This class manages sharing the basic configurations for CRUD behavior among
 * several cooperating CRUD processors.
 *
 * @author Dick Schoeller
 */
public class CrudParams {
    /** */
    private final GedDocumentFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public CrudParams(final GedDocumentFileLoader loader,
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
    public final GedDocumentFileLoader getLoader() {
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
     * @return the repository manager
     */
    public final RepositoryManagerMongo getRepositoryManager() {
        return repositoryManager;
    }
}
