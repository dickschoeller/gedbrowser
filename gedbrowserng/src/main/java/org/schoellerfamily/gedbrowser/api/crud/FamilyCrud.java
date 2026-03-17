package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.FamilyDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

import lombok.extern.slf4j.Slf4j;



/**
 * Represents family crud.
 *
 * @author Richard Schoeller
 */
@Slf4j
public class FamilyCrud
    extends OperationsEnabler<Family>
    implements CrudOperations<Family, FamilyDocument, ApiFamily>,
        ObjectCrud<ApiFamily> {

    /**
     * Creates a new FamilyCrud.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    public FamilyCrud(final GedObjectFileLoader loader,
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
    public FindableDocument<Family, FamilyDocument> getRepository() {
        return ((FamilyDocumentRepositoryMongo) getRepositoryManager().get(Family.class));
    }

    /**
     * Returns the ged class.
     *
     * @return the ged class
     */
    @Override
    public Class<Family> getGedClass() {
        return Family.class;
    }

    /**
     * Creates the one.
     *
     * @param db the db
     * @param family the family
     * @return the resulting api family
     */
    @Override
    public ApiFamily createOne(final String db, final ApiFamily family) {
        log.info("Entering create family in db: {}", db);
        return create(readRoot(getRepositoryManager(), db), family,
                (i, id) -> i.toBuilder().string(id).build());
    }

    /**
     * Executes read all.
     *
     * @param db the db
     * @return the resulting list
     */
    @Override
    public List<ApiFamily> readAll(final String db) {
        log.info("Entering read /dbs/{}/families", db);
        return convert(read(getRepositoryManager(), db));
    }

    /**
     * Executes read one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api family
     */
    @Override
    public ApiFamily readOne(final String db, final String id) {
        log.info("Entering read /dbs/{}/families/{}", db, id);
        return convert(read(getRepositoryManager(), db, id));
    }

    /**
     * Executes update one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param family the family
     * @return the resulting api family
     */
    @Override
    public ApiFamily updateOne(final String db, final String id,
            final ApiFamily family) {
        log.info("Entering update family in db: {}", db);
        if (!id.equals(family.getString())) {
            return null;
        }
        if (isLinked(family)) {
            return update(readRoot(getRepositoryManager(), db), family);
        }
        return delete(readRoot(getRepositoryManager(), db), id);
    }

    /**
     * @param family the family to check
     * @return true if the family has spouses or children
     */
    private boolean isLinked(final ApiFamily family) {
        return !family.getChildren().isEmpty() || !family.getSpouses().isEmpty();
    }

    /**
     * Returns the api family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api family
     */
    @Override
    public ApiFamily deleteOne(final String db, final String id) {
        return delete(readRoot(getRepositoryManager(), db), id);
    }
}
