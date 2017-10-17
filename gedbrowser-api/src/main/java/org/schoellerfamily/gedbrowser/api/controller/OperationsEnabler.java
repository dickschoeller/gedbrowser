package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @param <X> the data model type we are creating
 * @param <Y> the DB type associated with the type X
 * @author Dick Schoeller
 */
public abstract class OperationsEnabler<
    X extends GedObject, Y extends GedDocument<X>> {
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
     * Handles data conversion from DB model to API model.
     */
    private final DocumentToApiModelTransformer d2dm =
            new DocumentToApiModelTransformer();

    /**
     * @return the data model class
     */
    public abstract Class<X> getGedClass();

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


    /**
     * @return the repository for this type
     */
    @SuppressWarnings("unchecked")
    public FindableDocument<X, Y> getRepository() {
        return (FindableDocument<X, Y>)
                getRepositoryManager().get(getGedClass());
    }

    /**
     * @return the class the converts from DB model to API model
     */
    public final DocumentToApiModelTransformer getD2dm() {
        return d2dm;
    }
}
