package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * @author Dick Schoeller
 */
public class SourceCrud
    extends OperationsEnabler<Source, SourceDocument>
    implements CrudOperations<Source, SourceDocument, ApiSource> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public SourceCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FindableDocument<Source, SourceDocument> getRepository() {
        return getRepositoryManager().getSourceDocumentRepository();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Source> getGedClass() {
        return Source.class;
    }

    /**
     * @param db the name of the db to access
     * @param source the data for the source
     * @return the source as created
     */
    public ApiObject createSource(final String db,
            final ApiSource source) {
        logger.info("Entering create source in db: " + db);
        return create(readRoot(db), source, (i, id) -> new ApiSource(i, id));
    }

    /**
     * @param db the name of the db to access
     * @return the list of sources
     */
    public List<ApiSource> readSources(
            final String db) {
        logger.info("Entering sources, db: " + db);
        return getD2dm().convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the source
     */
    public ApiSource readSource(
            final String db,
            final String id) {
        logger.info("Entering source, db: " + db + ", id: " + id);
        return getD2dm().convert(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the source to update
     * @param source the data for the source
     * @return the source as created
     */
    public ApiObject updateSource(final String db,
            final String id,
            final ApiSource source) {
        logger.info("Entering update source in db: " + db);
        if (!id.equals(source.getString())) {
            return null;
        }
        return update(readRoot(db), source);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the deleted object
     */
    public ApiSource deleteSource(
            final String db,
            final String id) {
        return delete(readRoot(db), id);
    }
}
