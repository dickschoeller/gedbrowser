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
     * Creates a new OperationsEnabler.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    protected OperationsEnabler(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * Gets the ged class.
     *
     * @return the ged class
     */
    public abstract Class<X> getGedClass();

    /**
     * Gets the d2dm.
     *
     * @return the d2dm
     */
    public final DocumentToApiModelTransformer getD2dm() {
        return d2dm;
    }
}
