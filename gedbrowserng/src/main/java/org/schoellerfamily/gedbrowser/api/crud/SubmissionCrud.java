package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * @author Dick Schoeller
 */
public class SubmissionCrud
        extends OperationsEnabler<Submission, SubmissionDocument> implements
        CrudOperations<Submission, SubmissionDocument, ApiSubmission> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
        return getRepositoryManager().getSubmissionDocumentRepository();
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
    public ApiSubmission createSubmission(final String db,
            final ApiSubmission submission) {
        logger.info("Entering create submission in db: " + db);
        return create(readRoot(db), submission, (i,
                id) -> new ApiSubmission(i.getType(), id, i.getAttributes()));
    }

    /**
     * @param db the name of the db to access
     * @return the list of submissions
     */
    public List<ApiSubmission> readAll(final String db) {
        logger.info("Entering submissions, db: " + db);
        return getD2dm().convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the submission
     */
    public ApiSubmission readSubmission(final String db, final String id) {
        logger.info("Entering submission, db: " + db + ", id: " + id);
        return getD2dm().convert(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the submission to update
     * @param submission the data for the submission
     * @return the submission as created
     */
    public ApiSubmission updateOne(final String db, final String id,
            final ApiSubmission submission) {
        logger.info("Entering update submission in db: " + db);
        if (!id.equals(submission.getString())) {
            return null;
        }
        return update(readRoot(db), submission);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the deleted object
     */
    public ApiSubmission deleteSubmission(final String db, final String id) {
        return delete(readRoot(db), id);
    }
}
