package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * @author Dick Schoeller
 */
public class SubmitterCrud
        extends OperationsEnabler<Submitter, SubmitterDocument>
        implements CrudOperations<Submitter, SubmitterDocument, ApiSubmitter> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public SubmitterCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FindableDocument<Submitter, SubmitterDocument> getRepository() {
        return getRepositoryManager().getSubmitterDocumentRepository();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Submitter> getGedClass() {
        return Submitter.class;
    }

    /**
     * @param db the name of the db to access
     * @param submitter the data for the submitter
     * @return the submitter as created
     */
    public ApiObject createSubmitter(final String db,
            final ApiSubmitter submitter) {
        logger.info("Entering create submitter in db: " + db);
        return create(readRoot(db), submitter,
                (i, id) -> new ApiSubmitter(i.getType(), id, i.getAttributes(),
                        i.getName()));
    }

    /**
     * @param db the name of the db to access
     * @return the list of submitters
     */
    public List<ApiSubmitter> readSubmitters(final String db) {
        logger.info("Entering submitters, db: " + db);
        return convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @return the person
     */
    public ApiSubmitter readSubmitter(final String db, final String id) {
        logger.info("Entering submitter, db: " + db + ", id: " + id);
        return convert(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the submitter to update
     * @param submitter the data for the submitter
     * @return the submitter as created
     */
    public ApiObject updateSubmitter(final String db, final String id,
            final ApiSubmitter submitter) {
        logger.info("Entering update submitter in db: " + db);
        if (!id.equals(submitter.getString())) {
            return null;
        }
        return update(readRoot(db), submitter);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @return the deleted object
     */
    public ApiSubmitter deleteSubmitter(final String db, final String id) {
        return delete(readRoot(db), id);
    }
}
