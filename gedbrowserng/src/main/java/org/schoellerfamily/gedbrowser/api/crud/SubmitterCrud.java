package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmitterDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

import lombok.extern.slf4j.Slf4j;



/**
 * Represents submitter crud.
 *
 * @author Richard Schoeller
 */
@Slf4j
public class SubmitterCrud
    extends OperationsEnabler<Submitter>
    implements CrudOperations<Submitter, SubmitterDocument, ApiSubmitter>,
        ObjectCrud<ApiSubmitter> {

    /**
     * Creates a new SubmitterCrud.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    public SubmitterCrud(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * Returns the repository.
     *
     * @return the repository
     */
    @Override
    public FindableDocument<Submitter, SubmitterDocument> getRepository() {
        return ((SubmitterDocumentRepositoryMongo) getRepositoryManager().get(Submitter.class));
    }

    /**
     * Returns the ged class.
     *
     * @return the ged class
     */
    @Override
    public Class<Submitter> getGedClass() {
        return Submitter.class;
    }

    /**
     * Creates the one.
     *
     * @param db the db
     * @param submitter the submitter
     * @return the resulting api submitter
     */
    @Override
    public ApiSubmitter createOne(final String db,
            final ApiSubmitter submitter) {
        log.info("Entering create submitter in db: {}", db);
        return create(readRoot(getRepositoryManager(), db), submitter,
            (i, id) -> i.toBuilder().string(id).build());
    }

    /**
     * Executes read all.
     *
     * @param db the db
     * @return the resulting list
     */
    @Override
    public List<ApiSubmitter> readAll(final String db) {
        log.info("Entering submitters, db: {}", db);
        return convert(read(getRepositoryManager(), db));
    }

    /**
     * Executes read one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api submitter
     */
    @Override
    public ApiSubmitter readOne(final String db, final String id) {
        log.info("Entering submitter, db: {}, id: {}", db, id);
        return convert(read(getRepositoryManager(), db, id));
    }

    /**
     * Executes update one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param submitter the submitter
     * @return the resulting api submitter
     */
    @Override
    public ApiSubmitter updateOne(final String db, final String id,
            final ApiSubmitter submitter) {
        log.info("Entering update submitter in db: {}", db);
        if (!id.equals(submitter.getString())) {
            return null;
        }
        return update(readRoot(getRepositoryManager(), db), submitter);
    }

    /**
     * Returns the api submitter.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api submitter
     */
    @Override
    public ApiSubmitter deleteOne(final String db, final String id) {
        return delete(readRoot(getRepositoryManager(), db), id);
    }
}
