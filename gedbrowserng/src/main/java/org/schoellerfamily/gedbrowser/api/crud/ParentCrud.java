package org.schoellerfamily.gedbrowser.api.crud;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * @author Dick Schoeller
 */
public class ParentCrud extends RelationsCrud {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public ParentCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose parent we are adding
     * @param person the data for the parent
     * @return the person returned from the db
     */
    public ApiObject createParent(final String db, final String id,
            final ApiPerson person) {
        logger.info(
                "Entering create parent in db: " + db + " for person " + id);
        final ApiPerson oldPerson = readPerson(db, id);
        final ApiPerson newPerson = createPerson(db, person);
        final ApiFamily family = createFamily(db);
        addChildToFamily(family, oldPerson);
        addSpouseToFamily(family, newPerson);
        return crudUpdate(db, family, oldPerson, newPerson);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose parent we are adding
     * @param person the data for the spouse
     * @return the person returned from the db
     */
    public ApiObject linkParent(final String db, final String id,
            final ApiPerson person) {
        logger.info(
                "Entering link person: " + person.getString()
                + " in db: " + db
                + " as parent of person " + id);
        final ApiPerson oldPerson = readPerson(db, id);
        final ApiPerson newPerson = readPerson(db, person.getString());
        final ApiFamily family = createFamily(db);
        addChildToFamily(family, oldPerson);
        addSpouseToFamily(family, newPerson);
        return crudUpdate(db, family, oldPerson, newPerson);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTheLinkWeAreLookingFor(final ApiAttribute attribute,
            final String id) {
        return ("husband".equals(attribute.getType())
                || "wife".equals(attribute.getType()))
                && attribute.getString().equals(id);
    }
}
