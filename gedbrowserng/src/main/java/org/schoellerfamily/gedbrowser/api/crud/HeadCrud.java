package org.schoellerfamily.gedbrowser.api.crud;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * @author Dick Schoeller
 */
public class HeadCrud
    extends OperationsEnabler<Head, HeadDocument>
    implements CrudOperations<Head, HeadDocument, ApiHead> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public HeadCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FindableDocument<Head, HeadDocument> getRepository() {
        return getRepositoryManager().getHeadDocumentRepository();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Head> getGedClass() {
        return Head.class;
    }

    /**
     * @param db the name of the db to access
     * @return the one head
     */
    public ApiHead readHead(final String db) {
        logger.info("Entering head, db: " + db);
        return (ApiHead) getD2dm().convert(read(db)).get(0);
    }


    /**
     * @param db the name of the db to access
     * @return the list of heads
     */
    public List<ApiHead> readAll(final String db) {
        logger.info("Entering all head, db: " + db);
        final List<ApiHead> list = new ArrayList<>();
        list.add((ApiHead) getD2dm().convert(read(db)).get(0));
        return list;
    }

    /**
     * @param db the name of the db to access
     * @param head the data for the head
     * @return the head as created
     */
    public ApiHead updateHead(final String db, final ApiHead head) {
        logger.info("Entering update head in db: " + db);
        return update(readRoot(db), head);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the head (ignored)
     * @param head the data for the head
     * @return the head as created
     */
    public ApiHead updateOne(final String db, final String id,
            final ApiHead head) {
        logger.info("Entering update head in db: " + db);
        return updateHead(db, head);
    }
}
