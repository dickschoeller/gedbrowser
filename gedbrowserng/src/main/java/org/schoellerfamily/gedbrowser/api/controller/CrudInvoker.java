package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@RequiredArgsConstructor
public abstract class CrudInvoker {
    /** */
    private final GedDocumentFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    /**
     * @return the file loader
     */
    protected GedDocumentFileLoader getLoader() {
        return loader;
    }

    /**
     * @return the document type converter
     */
    protected GedObjectToGedDocumentMongoConverter getConverter() {
        return toDocConverter;
    }

    /**
     * @return the repository manager
     */
    protected RepositoryManagerMongo getManager() {
        return repositoryManager;
    }
}
