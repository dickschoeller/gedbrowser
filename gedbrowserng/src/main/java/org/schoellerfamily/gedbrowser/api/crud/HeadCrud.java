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
 * Represents head crud.
 *
 * @author Richard Schoeller
 */
@Slf4j
public class HeadCrud
    extends OperationsEnabler<Head>
    implements CrudOperations<Head, HeadDocument, ApiHead>,
        ObjectCrud<ApiHead> {

    /**
     * Creates a new HeadCrud.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    public HeadCrud(final GedObjectFileLoader loader,
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
    public FindableDocument<Head, HeadDocument> getRepository() {
        return ((HeadDocumentRepositoryMongo) getRepositoryManager().get(Head.class));
    }

    /**
     * Returns the ged class.
     *
     * @return the ged class
     */
    @Override
    public Class<Head> getGedClass() {
        return Head.class;
    }

    /**
     * Creates the one.
     *
     * @param db the db
     * @param head the head
     * @return the resulting api head
     */
    public ApiHead createOne(final String db, final ApiHead head) {
        throw new UnsupportedOperationException(
                "Can't create a head through the crud operations");
    }

    /**
     * Executes read one.
     *
     * @param db the db
     * @return the resulting api head
     */
    public ApiHead readOne(final String db) {
        log.info("Entering head, db: {}", db);
        return (ApiHead) getD2dm().convert(read(getRepositoryManager(), db)).get(0);
    }

    /**
     * Returns the api head.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api head
     */
    @Override
    public ApiHead readOne(final String db, final String id) {
        return readOne(db);
    }

    /**
     * Executes read all.
     *
     * @param db the db
     * @return the resulting list
     */
    @Override
    public List<ApiHead> readAll(final String db) {
        log.info("Entering all head, db: {}", db);
        return List.of((ApiHead) getD2dm().convert(read(getRepositoryManager(), db)).get(0));
    }

    /**
     * Executes update head.
     *
     * @param db the db
     * @param head the head
     * @return the resulting api head
     */
    public ApiHead updateHead(final String db, final ApiHead head) {
        log.info("Entering update head in db: {}", db);
        return update(readRoot(getRepositoryManager(), db), head);
    }

    /**
     * Executes update one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param head the head
     * @return the resulting api head
     */
    @Override
    public ApiHead updateOne(final String db, final String id,
            final ApiHead head) {
        log.info("Entering update head in db: {}", db);
        return updateHead(db, head);
    }

    /**
     * Executes delete one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api head
     */
    public ApiHead deleteOne(final String db, final String id) {
        throw new UnsupportedOperationException(
                "Can't delete a head through the crud operations");
    }
}
