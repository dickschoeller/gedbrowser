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
public class ChildCrud {
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
                "Entering create spouse in db: " + db + " for person " + id);
        final ApiPerson oldPerson = personCrud.readPerson(db, id);
        final ApiPerson newPerson = personCrud.createPerson(db, person);
        final ApiFamily newFamily = familyCrud.createFamily(db,
                new ApiFamily());
        newFamily.getAttributes().add(helper.spouseAttribute(oldPerson));
        newFamily.getAttributes().add(helper.childAttribute(newPerson));
        newPerson.getAttributes().add(helper.famcAttribute(newFamily));
        oldPerson.getAttributes().add(helper.famsAttribute(newFamily));
        familyCrud.updateFamily(db, newFamily.getString(), newFamily);
        personCrud.updatePerson(db, oldPerson.getString(), oldPerson);
        return personCrud.updatePerson(db, newPerson.getString(), newPerson);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose spouse we are adding
     * @param person the data for the spouse
     * @return the person linking to the new person
     */
    public ApiPerson createChildInFamily(final String db, final String id,
            final ApiPerson person) {
        logger.info(
                "Entering create spouse in db: " + db + " for person " + id);
        final ApiFamily newFamily = familyCrud.readFamily(db, id);
        final ApiPerson newPerson = personCrud.createPerson(db, person);
        newFamily.getAttributes().add(helper.childAttribute(newPerson));
        newPerson.getAttributes().add(helper.famcAttribute(newFamily));
        familyCrud.updateFamily(db, newFamily.getString(), newFamily);
        return personCrud.updatePerson(db, newPerson.getString(), newPerson);
    }
}
