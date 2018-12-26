package org.schoellerfamily.gedbrowser.api.crud;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * @author Dick Schoeller
 */
public class SpouseCrud extends RelationsCrud {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public SpouseCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose spouse we are adding
     * @param person the data for the spouse
     * @return the person returned from the db
     */
    public ApiPerson createSpouse(final String db, final String id,
            final ApiPerson person) {
        logger.info("Entering create spouse in db: " + db + " of person " + id);
        final ApiPerson oldPerson = readPerson(db, id);
        final ApiPerson newPerson = createPerson(db, person);
        final ApiFamily family = createFamily(db);
        addSpouseToFamily(family, newPerson);
        addSpouseToFamily(family, oldPerson);

        return crudUpdate(db, family, oldPerson, newPerson);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose spouse we are adding
     * @param person the data for the spouse (use the id to read from the db)
     * @return the person returned from the db
     */
    public ApiPerson linkSpouse(final String db, final String id,
            final ApiPerson person) {
        logger.info("Entering link person: " + person.getString()
                + " as spouse in db: " + db + " of person " + id);
        final ApiPerson oldPerson = readPerson(db, id);
        final ApiPerson newPerson = readPerson(db, person.getString());
        final ApiFamily family = createFamily(db);
        addSpouseToFamily(family, newPerson);
        addSpouseToFamily(family, oldPerson);

        return crudUpdate(db, family, oldPerson, newPerson);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family to which we are adding a spouse
     * @param person the data for the spouse
     * @return the person returned from the db
     */
    public ApiPerson createSpouseInFamily(final String db, final String id,
            final ApiPerson person) {
        logger.info("Entering create spouse in db: " + db + " in family " + id);
        final ApiPerson newPerson = createPerson(db, person);
        try {
            final ApiFamily family = readFamily(db, id);
            addSpouseToFamily(family, newPerson);
            return crudUpdate(db, family, newPerson);
        } catch (ObjectNotFoundException e) {
            return newPerson;
        }
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family to which we are linking a spouse
     * @param person the data for the spouse (use the id to read from db)
     * @return the person returned from the db
     */
    public ApiPerson linkSpouseInFamily(final String db, final String id,
            final ApiPerson person) {
        logger.info("Entering link person: " + person.getString() + " in db: "
                + db + " as spouse in family " + id);
        final ApiPerson newPerson = readPerson(db, person.getString());
        try {
            final ApiFamily family = readFamily(db, id);
            addSpouseToFamily(family, newPerson);
            return crudUpdate(db, family, newPerson);
        } catch (ObjectNotFoundException e) {
            return newPerson;
        }
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family to which we are linking a spouse
     * @param sid the id of the spouse to remove
     * @return the person returned from the db
     */
    public ApiPerson unlinkSpouseInFamily(final String db, final String id,
            final String sid) {
        logger.info("Entering unlink person: " + sid + " in db: " + db
                + " from being a spouse in family " + id);
        final ApiPerson person = readPerson(db, sid);
        try {
            final ApiFamily family = readFamily(db, id);
            removeSpouseFromFamily(family, person);
            return crudUpdate(db, family, person);
        } catch (ObjectNotFoundException e) {
            final ApiAttribute fams = findFamsAttribute(id, person);
            person.getFams().remove(fams);
            crudUpdate(db, person);
            return person;
        }
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
