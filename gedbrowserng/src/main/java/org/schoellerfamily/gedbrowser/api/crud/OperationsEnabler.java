package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * @param <X> the data model type we are creating
 * @author Dick Schoeller
 */
public abstract class OperationsEnabler<
    X extends GedObject> extends CrudParams {

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
    protected OperationsEnabler(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * @return the data model class
     */
    public abstract Class<X> getGedClass();

    /**
     * @return the class the converts from DB model to API model
     */
    public final DocumentToApiModelTransformer getD2dm() {
        return d2dm;
    }
}
