package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmissionDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public class SubmissionCrud
    extends OperationsEnabler<Submission, SubmissionDocument>
    implements CrudOperations<Submission, SubmissionDocument, ApiSubmission>,
        ObjectCrud<ApiSubmission> {

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public SubmissionCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FindableDocument<Submission, SubmissionDocument> getRepository() {
        return ((SubmissionDocumentRepositoryMongo) getRepositoryManager().get(Submission.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Submission> getGedClass() {
        return Submission.class;
    }

    /**
     * @param db the name of the db to access
     * @param submission the data for the submission
     * @return the submission as created
     */
    @Override
    public ApiSubmission createOne(final String db,
            final ApiSubmission submission) {
        log.info("Entering create submission in db: " + db);
        return create(readRoot(getRepositoryManager(), db), submission, (i,
                id) -> new ApiSubmission(i.getType(), id, i.getAttributes()));
    }

    /**
     * @param db the name of the db to access
     * @return the list of submissions
     */
    @Override
    public List<ApiSubmission> readAll(final String db) {
        log.info("Entering submissions, db: " + db);
        return getD2dm().convert(read(getRepositoryManager(), db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the submission
     */
    @Override
    public ApiSubmission readOne(final String db, final String id) {
        log.info("Entering submission, db: " + db + ", id: " + id);
        return getD2dm().convert(read(getRepositoryManager(), db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the submission to update
     * @param submission the data for the submission
     * @return the submission as created
     */
    @Override
    public ApiSubmission updateOne(final String db, final String id,
            final ApiSubmission submission) {
        log.info("Entering update submission in db: " + db);
        if (!id.equals(submission.getString())) {
            return null;
        }
        return update(readRoot(getRepositoryManager(), db), submission);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the deleted object
     */
    @Override
    public ApiSubmission deleteOne(final String db, final String id) {
        return delete(readRoot(getRepositoryManager(), db), id);
    }
}
