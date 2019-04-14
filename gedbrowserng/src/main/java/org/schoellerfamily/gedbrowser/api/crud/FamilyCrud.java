package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * @author Dick Schoeller
 */
public class FamilyCrud
    extends OperationsEnabler<Family, FamilyDocument>
    implements CrudOperations<Family, FamilyDocument, ApiFamily>,
        ObjectCrud<ApiFamily> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public FamilyCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FindableDocument<Family, FamilyDocument> getRepository() {
        return getRepositoryManager().getFamilyDocumentRepository();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Family> getGedClass() {
        return Family.class;
    }

    /**
     * @param db the name of the db to access
     * @param family the data for the family
     * @return the family as created
     */
    @Override
    public ApiFamily createOne(final String db, final ApiFamily family) {
        logger.info("Entering create family in db: " + db);
        return create(readRoot(db), family,
                (i, id) -> new ApiFamily(i, id));
    }

    /**
     * @param db the name of the db to access
     * @return the list of families
     */
    @Override
    public List<ApiFamily> readAll(final String db) {
        logger.info("Entering read /dbs/" + db + "/families");
        return convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @return the family
     */
    @Override
    public ApiFamily readOne(final String db, final String id) {
        logger.info("Entering read /dbs/" + db + "/families/" + id);
        return convert(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family to update
     * @param family the data for the family
     * @return the family as created
     */
    @Override
    public ApiFamily updateOne(final String db, final String id,
            final ApiFamily family) {
        logger.info("Entering update family in db: " + db);
        if (!id.equals(family.getString())) {
            return null;
        }
        if (isLinked(family)) {
            return update(readRoot(db), family);
        }
        return delete(readRoot(db), id);
    }

    /**
     * @param family the family to check
     * @return true if the family has spouses or children
     */
    private boolean isLinked(final ApiFamily family) {
        if (!family.getChildren().isEmpty()) {
            return true;
        }
        return !family.getSpouses().isEmpty();
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @return the deleted object
     */
    @Override
    public ApiFamily deleteOne(final String db, final String id) {
        return delete(readRoot(db), id);
    }
}
