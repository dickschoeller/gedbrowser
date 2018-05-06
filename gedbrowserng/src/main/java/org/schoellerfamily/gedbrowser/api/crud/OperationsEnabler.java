package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * @param <X> the data model type we are creating
 * @param <Y> the DB type associated with the type X
 * @author Dick Schoeller
 */
public abstract class OperationsEnabler<
    X extends GedObject, Y extends GedDocument<X>> {
    /** */
    private final GedDocumentFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    /**
     * Handles data conversion from DB model to API model.
     */
    private final DocumentToApiModelTransformer d2dm =
            new DocumentToApiModelTransformer();

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public OperationsEnabler(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        this.loader = loader;
        this.toDocConverter = toDocConverter;
        this.repositoryManager = repositoryManager;
    }

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
     * @return the class the converts from DB model to API model
     */
    public final DocumentToApiModelTransformer getD2dm() {
        return d2dm;
    }
}
