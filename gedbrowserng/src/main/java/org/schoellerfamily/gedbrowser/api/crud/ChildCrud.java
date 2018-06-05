package org.schoellerfamily.gedbrowser.api.crud;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * @author Dick Schoeller
 */
public final class ChildCrud extends RelationsCrud {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public ChildCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose child we are adding
     * @param person the data for the child
     * @return the person linking to the old person
     */
    public ApiPerson createChild(final String db, final String id,
            final ApiPerson person) {
        logger.info(
                "Entering create child in db: " + db + " for person " + id);
        final ApiPerson oldPerson = readPerson(db, id);
        final ApiPerson newPerson = createPerson(db, person);
        final ApiFamily newFamily = createFamily(db);
        addChildToFamily(newFamily, newPerson);
        addSpouseToFamily(newFamily, oldPerson);
        return crudUpdate(db, newFamily, oldPerson, newPerson);
    }

    /**
     * @param db the name of the db to update
     * @param id the id of the person who will be a parent in new family
     * @param person the person object to link as a child
     * @return the person post modification
     */
    public ApiPerson linkChild(final String db, final String id,
            final ApiPerson person) {
        logger.info(
                "Entering link person: " + person.getString()
                + " in db: " + db
                + " as a child of person: " + id
                + ", creating a new family");
        final ApiPerson oldPerson = readPerson(db, id);
        final ApiPerson newPerson = readPerson(db, person.getString());
        final ApiFamily family = createFamily(db);
        addChildToFamily(family, newPerson);
        addSpouseToFamily(family, oldPerson);
        return crudUpdate(db, family, oldPerson, newPerson);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family whose child we are adding
     * @param person the data for the spouse
     * @return the person linking to the new person
     */
    public ApiPerson createChildInFamily(final String db, final String id,
            final ApiPerson person) {
        logger.info(
                "Entering create child in db: " + db + " for family " + id);
        final ApiFamily family = readFamily(db, id);
        final ApiPerson newPerson = createPerson(db, person);
        addChildToFamily(family, newPerson);
        return crudUpdate(db, family, newPerson);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family whose child we are linking
     * @param person the data for the child (only need the string field)
     * @return the person linking to the new person
     */
    public ApiPerson linkChildInFamily(final String db, final String id,
            final ApiPerson person) {
        logger.info(
                "Entering link person: " + person.getString()
                + " in db: " + db
                + " as a child of family: " + id);
        final ApiFamily family = readFamily(db, id);
        final ApiPerson foundPerson = readPerson(db, person.getString());
        addChildToFamily(family, foundPerson);
        return crudUpdate(db, family, foundPerson);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family whose child we are unlinking
     * @param child the id of the child to unlink
     * @return the person linking to the new person
     */
    public ApiPerson unlinkChild(final String db, final String id,
            final String child) {
        logger.info("Entering unlink person: " + child
                + " in db: " + db + " from family: " + id);
        final ApiFamily family = readFamily(db, id);
        final ApiPerson childPerson = readPerson(db, child);
        removeChildFromFamily(family, childPerson);
        removeFamilyFromChild(family, childPerson);
        return crudUpdate(db, family, childPerson);
    }

    /**
     * @param family the family referred to by the famc link to remove
     * @param person the person to remove from
     */
    private void removeFamilyFromChild(final ApiFamily family,
            final ApiPerson person) {
        for (final ApiAttribute famc : person.getFamc()) {
            if (famc.getString().equals(family.getString())) {
                person.getFamc().remove(famc);
                break;
            }
        }
    }

    /**
     * @param family the family to remove from
     * @param person the person who referred to by the child link to remove
     */
    private void removeChildFromFamily(final ApiFamily family,
            final ApiPerson person) {
        for (final ApiAttribute childlink : family.getChildren()) {
            if (childlink.getString().equals(person.getString())) {
                family.getChildren().remove(childlink);
                break;
            }
        }
    }
}
