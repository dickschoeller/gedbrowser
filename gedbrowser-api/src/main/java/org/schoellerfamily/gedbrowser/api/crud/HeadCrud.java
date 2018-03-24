package org.schoellerfamily.gedbrowser.api.crud;

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
     * @return the list of sources
     */
    public ApiHead readHead(final String db) {
        logger.info("Entering head, db: " + db);
        return (ApiHead) getD2dm().convert(read(db)).get(0);
    }
}
