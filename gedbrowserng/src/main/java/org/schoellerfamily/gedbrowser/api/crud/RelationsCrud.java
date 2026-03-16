package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily.ApiFamilyBuilder;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson.ApiPersonBuilder;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * @author Dick Schoeller
 */
public abstract class RelationsCrud extends CrudParams implements LinkCrud {
    private final transient CrudHelper helper = new CrudHelper();

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    protected RelationsCrud(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param person the data for the new person
     * @return the person returned from the db
     */
    protected final ApiPerson createPerson(final String db, final ApiPerson person) {
        return personCrud().createOne(db, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person to read
     * @return the person returned from the db
     */
    protected final ApiPerson readPerson(final String db, final String id) {
        return personCrud().readOne(db, id);
    }

    /**
     * @param db the name of the db to access
     * @return the family returned from the db
     */
    protected final ApiFamily createFamily(final String db) {
        return familyCrud().createOne(db, ApiFamily.builder().build());
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family to read
     * @return the family returned from the db
     */
    protected final ApiFamily readFamily(final String db, final String id) {
        return familyCrud().readOne(db, id);
    }

    /**
     * @param family the family to add the person to
     * @param person the person to add
     */
    protected final void addChildToFamily(final ApiFamilyBuilder<?, ?> family,
            final ApiPersonBuilder<?, ?> person) {
        family.child(helper.childAttribute(person));
        person.famc(helper.famcAttribute(family));
    }

    /**
     * @param family the family to add the person to
     * @param person the person to add
     */
    protected final void addSpouseToFamily(final ApiFamilyBuilder<?, ?> family,
            final ApiPersonBuilder<?, ?> person) {
        person.fams(helper.famsAttribute(family));
        this.addSpouseAttribute(family, helper.spouseAttribute(person));
    }

    /**
     * @param family the family from which we will remove a spouse
     * @param person the person to remove as a spouse
     */
    protected final void removeSpouseFromFamily(final ApiFamilyBuilder<?, ?> family,
            final ApiPersonBuilder<?, ?> person) {
        final ApiAttribute spouse = findSpouseAttribute(family, person);
        if (spouse != null) {
            family.getSpouses().remove(spouse);
        }
        final ApiAttribute fams = findFamsAttribute(family, person);
        if (fams != null) {
            person.getFamss().remove(fams);
        }
    }

    private ApiAttribute findSpouseAttribute(final ApiFamilyBuilder<?, ?> family,
            final ApiPersonBuilder<?, ?> person) {
        for (final ApiAttribute spouse : family.getSpouses()) {
            if (spouse.getString().equals(person.getString())) {
                return spouse;
            }
        }
        return null;
    }

    private ApiAttribute findFamsAttribute(final ApiFamilyBuilder<?, ?> family,
            final ApiPersonBuilder<?, ?> person) {
        return findFamsAttribute(family.getString(), person);
    }


    /**
     * @param fid the family that should be pointed to by fams
     * @param person the person we are searching
     * @return the fams attribute
     */
    protected ApiAttribute findFamsAttribute(final String fid,
            final ApiPersonBuilder<?, ?> person) {
        for (final ApiAttribute fams : person.getFamss()) {
            if (fams.getString().equals(fid)) {
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
        familyCrud().updateOne(db, newFamily.getString(), newFamily);
        return crudUpdate(db, newPersons);
    }

    /**
     * @param db the name of the db to update
     * @param newPersons the persons linked to the family
     * @return the person
     */
    protected final ApiPerson crudUpdate(final String db,
            final ApiPerson... newPersons) {
        ApiPerson person = null;
        for (final ApiPerson newPerson : newPersons) {
            person = personCrud().updateOne(db, newPerson.getString(), newPerson);
        }
        return person;
    }

    /**
     * Add the spouses in husband/wife order.
     *
     * @param newFamily the family to modify
     * @param spouseAttribute the spouse to add
     */
    protected final void addSpouseAttribute(final ApiFamilyBuilder<?, ?> newFamily,
            final ApiAttribute spouseAttribute) {
        if ("husband".equals(spouseAttribute.getType())) {
            newFamily.addSpouse(0, spouseAttribute);
        } else {
            newFamily.spouse(spouseAttribute);
        }
    }
}
