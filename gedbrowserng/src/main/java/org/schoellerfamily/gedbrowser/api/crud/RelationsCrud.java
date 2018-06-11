package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * @author Dick Schoeller
 */
public class RelationsCrud {
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
    public RelationsCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        this.personCrud = new PersonCrud(loader, toDocConverter,
                repositoryManager);
        this.familyCrud = new FamilyCrud(loader, toDocConverter,
                repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param person the data for the new person
     * @return the person returned from the db
     */
    protected final ApiPerson createPerson(final String db,
            final ApiPerson person) {
        return personCrud.createPerson(db, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person to read
     * @return the person returned from the db
     */
    protected final ApiPerson readPerson(final String db, final String id) {
        return personCrud.readPerson(db, id);
    }

    /**
     * @param db the name of the db to access
     * @return the family returned from the db
     */
    protected final ApiFamily createFamily(final String db) {
        return familyCrud.createFamily(db, new ApiFamily());
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family to read
     * @return the family returned from the db
     */
    protected final ApiFamily readFamily(final String db, final String id) {
        return familyCrud.readFamily(db, id);
    }

    /**
     * @param family the family to add the person to
     * @param person the person to add
     */
    protected final void addChildToFamily(final ApiFamily family,
            final ApiPerson person) {
        family.getChildren().add(helper.childAttribute(person));
        person.getFamc().add(helper.famcAttribute(family));
    }

    /**
     * @param family the family to add the person to
     * @param person the person to add
     */
    protected final void addSpouseToFamily(final ApiFamily family,
            final ApiPerson person) {
        this.addSpouseAttribute(family, helper.spouseAttribute(person));
        person.getFams().add(helper.famsAttribute(family));
    }

    /**
     * @param family the family from which we will remove a spouse
     * @param person the person to remove as a spouse
     */
    protected final void removeSpouseFromFamily(final ApiFamily family,
            final ApiPerson person) {
        final ApiAttribute spouse = findSpouseAttribute(family, person);
        if (spouse != null) {
            family.getSpouses().remove(spouse);
        }
        final ApiAttribute fams = findFamsAttribute(family, person);
        if (fams != null) {
            person.getFams().remove(fams);
        }
    }

    /**
     * @param family the family that we are searching
     * @param person the person who should be a spouse
     * @return the spouse attribute
     */
    private ApiAttribute findSpouseAttribute(final ApiFamily family,
            final ApiPerson person) {
        for (final ApiAttribute spouse : family.getSpouses()) {
            if (spouse.getString().equals(person.getString())) {
                return spouse;
            }
        }
        return null;
    }

    /**
     * @param family the family that should be pointed to by fams
     * @param person the person we are searching
     * @return the fams attribute
     */
    private ApiAttribute findFamsAttribute(final ApiFamily family,
            final ApiPerson person) {
        for (final ApiAttribute fams : person.getFams()) {
            if (fams.getString().equals(family.getString())) {
                person.getFams().remove(fams);
                return fams;
            }
        }
        return null;
    }

    /**
     * @param db the name of the db to update
     * @param newFamily the family to modify
     * @param newPersons the persons linked to the family
     * @return the person
     */
    protected final ApiPerson crudUpdate(final String db,
            final ApiFamily newFamily, final ApiPerson... newPersons) {
        familyCrud.updateFamily(db, newFamily.getString(), newFamily);
        ApiPerson person = null;
        for (final ApiPerson newPerson : newPersons) {
            person = personCrud
                    .updatePerson(db, newPerson.getString(), newPerson);
        }
        return person;
    }

    /**
     * Add the spouses in husband/wife order.
     *
     * @param newFamily the family to modify
     * @param spouseAttribute the spouse to add
     */
    protected final void addSpouseAttribute(final ApiFamily newFamily,
            final ApiAttribute spouseAttribute) {
        if ("husband".equals(spouseAttribute.getType())) {
            newFamily.getSpouses().add(0, spouseAttribute);
        } else {
            newFamily.getSpouses().add(spouseAttribute);
        }
    }

}
