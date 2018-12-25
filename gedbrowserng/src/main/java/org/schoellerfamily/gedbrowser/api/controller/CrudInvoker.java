package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class CrudInvoker {
    /** */
    @Autowired
    private transient GedDocumentFileLoader loader;

    /** */
    @Autowired
    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

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
