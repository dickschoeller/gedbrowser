package org.schoellerfamily.gedbrowser.api.crud;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * @author Dick Schoeller
 */
public class ParentCrud {
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
    public ParentCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        this.personCrud = new PersonCrud(loader, toDocConverter,
                repositoryManager);
        this.familyCrud = new FamilyCrud(loader, toDocConverter,
                repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose spouse we are adding
     * @param person the data for the spouse
     * @return the new person
     */
    public ApiObject createParent(final String db, final String id,
            final ApiPerson person) {
        logger.info(
                "Entering create spouse in db: " + db + " for person " + id);
        final ApiPerson oldPerson = personCrud.readPerson(db, id);
        final ApiPerson newPerson = personCrud.createPerson(db, person);
        final ApiFamily newFamily = familyCrud.createFamily(db,
                new ApiFamily());
        newFamily.getChildren().add(helper.childAttribute(oldPerson));
        newFamily.getSpouses().add(helper.spouseAttribute(newPerson));
        newPerson.getFams().add(helper.famsAttribute(newFamily));
        oldPerson.getFamc().add(helper.famcAttribute(newFamily));
        familyCrud.updateFamily(db, newFamily.getString(), newFamily);
        personCrud.updatePerson(db, oldPerson.getString(), oldPerson);
        return personCrud.updatePerson(db, newPerson.getString(), newPerson);
    }
}