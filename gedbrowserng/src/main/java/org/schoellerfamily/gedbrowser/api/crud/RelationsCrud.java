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
 * Represents relations crud.
 *
 * @author Richard Schoeller
 */
public abstract class RelationsCrud extends CrudParams implements LinkCrud {
    /** */
    private final transient CrudHelper helper = new CrudHelper();

    /**
     * Creates a new RelationsCrud.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    protected RelationsCrud(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * Creates the person.
     *
     * @param db the db
     * @param person the person
     * @return the resulting api person
     */
    protected final ApiPerson createPerson(final String db, final ApiPerson person) {
        return personCrud().createOne(db, person);
    }

    /**
     * Returns the api person.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api person
     */
    protected final ApiPerson readPerson(final String db, final String id) {
        return personCrud().readOne(db, id);
    }

    /**
     * Creates the family.
     *
     * @param db the db
     * @return the resulting api family
     */
    protected final ApiFamily createFamily(final String db) {
        return familyCrud().createOne(db, ApiFamily.builder().build());
    }

    /**
     * Returns the api family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api family
     */
    protected final ApiFamily readFamily(final String db, final String id) {
        return familyCrud().readOne(db, id);
    }

    /**
     * Executes add child to family.
     *
     * @param family the family
     * @param person the person
     */
    protected final void addChildToFamily(final ApiFamilyBuilder<?, ?> family,
            final ApiPersonBuilder<?, ?> person) {
        family.child(helper.childAttribute(person));
        person.famc(helper.famcAttribute(family));
    }

    /**
     * Executes add spouse to family.
     *
     * @param family the family
     * @param person the person
     */
    protected final void addSpouseToFamily(final ApiFamilyBuilder<?, ?> family,
            final ApiPersonBuilder<?, ?> person) {
        person.fams(helper.famsAttribute(family));
        this.addSpouseAttribute(family, helper.spouseAttribute(person));
    }

    /**
     * Executes remove spouse from family.
     *
     * @param family the family
     * @param person the person
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
     * Finds the fams attribute.
     *
     * @param fid the unique identifier for f
     * @param person the person
     * @return the resulting api attribute
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
     * Executes crud update.
     *
     * @param db the db
     * @param newFamily the new family
     * @param newPersons the new persons
     * @return the resulting api person
     */
    protected final ApiPerson crudUpdate(final String db,
            final ApiFamily newFamily, final ApiPerson... newPersons) {
        familyCrud().updateOne(db, newFamily.getString(), newFamily);
        return crudUpdate(db, newPersons);
    }

    /**
     * Executes crud update.
     *
     * @param db the db
     * @param newPersons the new persons
     * @return the resulting api person
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
