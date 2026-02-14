package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.HeadDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public class HeadCrud
    extends OperationsEnabler<Head>
    implements CrudOperations<Head, HeadDocument, ApiHead>,
        ObjectCrud<ApiHead> {

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public HeadCrud(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FindableDocument<Head, HeadDocument> getRepository() {
        return ((HeadDocumentRepositoryMongo) getRepositoryManager().get(Head.class));
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
     * @param head a head object to create
     * @return the head
     * @throws UnsupportedOperationException always
     */
    public ApiHead createOne(final String db, final ApiHead head) {
        throw new UnsupportedOperationException(
                "Can't create a head through the crud operations");
    }

    /**
     * @param db the name of the db to access
     * @return the one head
     */
    public ApiHead readOne(final String db) {
        log.info("Entering head, db: {}", db);
        return (ApiHead) getD2dm().convert(read(getRepositoryManager(), db)).get(0);
    }

    /**
     * @param db the name of the db to access
     * @param id the id is not used here (just filling an API)
     * @return the one head
     */
    @Override
    public ApiHead readOne(final String db, final String id) {
        return readOne(db);
    }

    /**
     * @param db the name of the db to access
     * @return the list of heads
     */
    @Override
    public List<ApiHead> readAll(final String db) {
        log.info("Entering all head, db: {}", db);
        return List.of((ApiHead) getD2dm().convert(read(getRepositoryManager(), db)).get(0));
    }

    /**
     * @param db the name of the db to access
     * @param head the data for the head
     * @return the head as created
     */
    public ApiHead updateHead(final String db, final ApiHead head) {
        log.info("Entering update head in db: {}", db);
        return update(readRoot(getRepositoryManager(), db), head);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the head (ignored)
     * @param head the data for the head
     * @return the head as created
     */
    @Override
    public ApiHead updateOne(final String db, final String id,
            final ApiHead head) {
        log.info("Entering update head in db: {}", db);
        return updateHead(db, head);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of a head object to delete
     * @return the head
     * @throws UnsupportedOperationException always
     */
    public ApiHead deleteOne(final String db, final String id) {
        throw new UnsupportedOperationException(
                "Can't delete a head through the crud operations");
    }
}
