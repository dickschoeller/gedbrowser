package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmissionDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

import lombok.extern.slf4j.Slf4j;



/**
 * Represents submission crud.
 *
 * @author Richard Schoeller
 */
@Slf4j
public class SubmissionCrud
    extends OperationsEnabler<Submission>
    implements CrudOperations<Submission, SubmissionDocument, ApiSubmission>,
        ObjectCrud<ApiSubmission> {

    /**
     * Creates a new SubmissionCrud.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    public SubmissionCrud(final GedObjectFileLoader loader,
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
    public FindableDocument<Submission, SubmissionDocument> getRepository() {
        return ((SubmissionDocumentRepositoryMongo) getRepositoryManager().get(Submission.class));
    }

    /**
     * Returns the ged class.
     *
     * @return the ged class
     */
    @Override
    public Class<Submission> getGedClass() {
        return Submission.class;
    }

    /**
     * Creates the one.
     *
     * @param db the db
     * @param submission the submission
     * @return the resulting api submission
     */
    @Override
    public ApiSubmission createOne(final String db,
            final ApiSubmission submission) {
        log.info("Entering create submission in db: {}", db);
        return create(readRoot(getRepositoryManager(), db), submission,
            (i, id) -> i.toBuilder().string(id).build());
    }

    /**
     * Executes read all.
     *
     * @param db the db
     * @return the resulting list
     */
    @Override
    public List<ApiSubmission> readAll(final String db) {
        log.info("Entering submissions, db: {}", db);
        return getD2dm().convert(read(getRepositoryManager(), db));
    }

    /**
     * Executes read one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api submission
     */
    @Override
    public ApiSubmission readOne(final String db, final String id) {
        log.info("Entering submission, db: {}, id: {}", db, id);
        return getD2dm().convert(read(getRepositoryManager(), db, id));
    }

    /**
     * Executes update one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param submission the submission
     * @return the resulting api submission
     */
    @Override
    public ApiSubmission updateOne(final String db, final String id,
            final ApiSubmission submission) {
        log.info("Entering update submission in db: {}", db);
        if (!id.equals(submission.getString())) {
            return null;
        }
        return update(readRoot(getRepositoryManager(), db), submission);
    }

    /**
     * Returns the api submission.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api submission
     */
    @Override
    public ApiSubmission deleteOne(final String db, final String id) {
        return delete(readRoot(getRepositoryManager(), db), id);
    }
}
