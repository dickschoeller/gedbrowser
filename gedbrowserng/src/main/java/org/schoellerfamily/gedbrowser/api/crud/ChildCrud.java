package org.schoellerfamily.gedbrowser.api.crud;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * @author Dick Schoeller
 */
public final class ChildCrud {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** Helper. */
    private final transient CrudHelper helper = new CrudHelper();

    /** Handles all of the basic CRUD operations on persons. */
    private final PersonCrud personCrud;

    /** Handles all of the basic CRUD operations on families. */
    private final FamilyCrud familyCrud;

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public ChildCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        this.personCrud = new PersonCrud(loader, toDocConverter,
                repositoryManager);
        this.familyCrud = new FamilyCrud(loader, toDocConverter,
                repositoryManager);
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
        final ApiPerson oldPerson = personCrud.readPerson(db, id);
        final ApiPerson newPerson = personCrud.createPerson(db, person);
        final ApiFamily newFamily = familyCrud.createFamily(db,
                new ApiFamily());
        newFamily.getSpouses().add(helper.spouseAttribute(oldPerson));
        addNewPersonToFamilyChildren(newFamily, newPerson);
        oldPerson.getFams().add(helper.famsAttribute(newFamily));
        return crudUpdate(db, newFamily, oldPerson, newPerson);
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
        final ApiFamily newFamily = familyCrud.readFamily(db, id);
        final ApiPerson newPerson = personCrud.createPerson(db, person);
        addNewPersonToFamilyChildren(newFamily, newPerson);
        return crudUpdate(db, newFamily, newPerson);
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
                "Entering link child in db: " + db + " for family " + id);
        final ApiFamily newFamily = familyCrud.readFamily(db, id);
        final ApiPerson newPerson =
                personCrud.readPerson(db, person.getString());
        addNewPersonToFamilyChildren(newFamily, newPerson);
        return crudUpdate(db, newFamily, newPerson);
    }

    /**
     * @param db the name of the db to update
     * @param id the id of the family to modify
     * @param person the person object to link as a child
     * @return the person post modification
     */
    public ApiPerson linkChild(final String db, final String id,
            final ApiPerson person) {
        logger.info(
                "Entering create child in db: " + db + " for person " + id);
        final ApiPerson oldPerson = personCrud.readPerson(db, id);
        final ApiPerson newPerson = personCrud.readPerson(db,
                person.getString());
        final ApiFamily newFamily = familyCrud.createFamily(db,
                new ApiFamily());
        newFamily.getSpouses().add(helper.spouseAttribute(oldPerson));
        addNewPersonToFamilyChildren(newFamily, newPerson);
        oldPerson.getFams().add(helper.famsAttribute(newFamily));
        return crudUpdate(db, newFamily, oldPerson, newPerson);
    }

    /**
     * @param newFamily the family to add the person to
     * @param newPerson the person to add
     */
    private void addNewPersonToFamilyChildren(final ApiFamily newFamily,
            final ApiPerson newPerson) {
        newFamily.getChildren().add(helper.childAttribute(newPerson));
        newPerson.getFamc().add(helper.famcAttribute(newFamily));
    }

    /**
     * @param db the name of the db to update
     * @param newFamily the family to modify
     * @param newPersons the persons linked to the family
     * @return the person
     */
    private ApiPerson crudUpdate(final String db, final ApiFamily newFamily, final ApiPerson... newPersons) {
        familyCrud.updateFamily(db, newFamily.getString(), newFamily);
        ApiPerson person = null;
        for (final ApiPerson newPerson : newPersons) {
            person = personCrud.updatePerson(db, newPerson.getString(), newPerson);
        }
        return person;
    }
}
