package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily.ApiFamilyBuilder;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson.ApiPersonBuilder;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

import lombok.extern.slf4j.Slf4j;



/**
 * Represents child crud.
 *
 * @author Richard Schoeller
 */
@Slf4j
public final class ChildCrud extends RelationsCrud {

    /**
     * Creates a new ChildCrud.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    public ChildCrud(final GedObjectFileLoader loader,
        final GedObjectToGedDocumentMongoConverter toDocConverter,
        final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * Creates the child.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    public ApiPerson createChild(final String db, final String id, final ApiPerson person) {
        log.info("Entering create child in db: {} for person {}", db, id);
        final ApiPersonBuilder<?, ?> oldPerson = readPerson(db, id).toBuilder();
        final ApiPersonBuilder<?, ?> newPerson = createPerson(db, person).toBuilder();
        final ApiFamilyBuilder<?, ?> newFamily = createFamily(db).toBuilder();
        addChildToFamily(newFamily, newPerson);
        addSpouseToFamily(newFamily, oldPerson);
        return crudUpdate(db, newFamily.build(), oldPerson.build(), newPerson.build());
    }

    /**
     * Executes link child.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    public ApiPerson linkChild(final String db, final String id, final ApiPerson person) {
        log.info(
            "Entering link person: {} in db: {} as a child of person: {}, creating a new family",
            person.getString(), db, id);
        final ApiPersonBuilder<?, ?> oldPerson = readPerson(db, id).toBuilder();
        final ApiPersonBuilder<?, ?> newPerson = readPerson(db, person.getString()).toBuilder();
        final ApiFamilyBuilder<?, ?> family = createFamily(db).toBuilder();
        addChildToFamily(family, newPerson);
        addSpouseToFamily(family, oldPerson);
        return crudUpdate(db, family.build(), oldPerson.build(), newPerson.build());
    }

    /**
     * Creates the child in family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    public ApiPerson createChildInFamily(final String db, final String id, final ApiPerson person) {
        log.info("Entering create child in db: {} for family {}", db, id);
        try {
            final ApiFamilyBuilder<?, ?> family = readFamily(db, id).toBuilder();
            final ApiPersonBuilder<?, ?> newPerson = createPerson(db, person).toBuilder();
            addChildToFamily(family, newPerson);
            return crudUpdate(db, family.build(), newPerson.build());
        } catch (ObjectNotFoundException e) {
            return null;
        }
    }

    /**
     * Executes link child in family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    public ApiPerson linkChildInFamily(final String db, final String id, final ApiPerson person) {
        log.info("Entering link person: {} in db: {} as a child of family: {}", person.getString(),
            db, id);
        final ApiPersonBuilder<?, ?> foundPerson = readPerson(db, person.getString()).toBuilder();
        try {
            final ApiFamilyBuilder<?, ?> family = readFamily(db, id).toBuilder();
            addChildToFamily(family, foundPerson);
            return crudUpdate(db, family.build(), foundPerson.build());
        } catch (ObjectNotFoundException e) {
            return foundPerson.build();
        }
    }

    /**
     * Executes unlink child.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param child the child
     * @return the resulting api person
     */
    public ApiPerson unlinkChild(final String db, final String id, final String child) {
        log.info("Entering unlink person: {} in db: {} from family: {}", child, db, id);
        final ApiPersonBuilder<?, ?> childPerson = readPerson(db, child).toBuilder();
        removeFamilyFromChild(id, childPerson);
        try {
            final ApiFamilyBuilder<?, ?> family = readFamily(db, id).toBuilder();
            removeChildFromFamily(family, child);
            return crudUpdate(db, family.build(), childPerson.build());
        } catch (ObjectNotFoundException e) {
            return crudUpdate(db, childPerson.build());
        }
    }

    /**
     * Executes remove family from child.
     *
     * @param fid the unique identifier for f
     * @param person the person
     */
    protected void removeFamilyFromChild(final String fid, final ApiPersonBuilder<?, ?> person) {
        for (final ApiAttribute famc : person.getFamcs()) {
            if (famc.getString().equals(fid)) {
                person.getFamcs().remove(famc);
                break;
            }
        }
    }

    private void removeChildFromFamily(final ApiFamilyBuilder<?, ?> family, final String cid) {
        for (final ApiAttribute childlink : family.getChildren()) {
            if (childlink.getString().equals(cid)) {
                family.getChildren().remove(childlink);
                break;
            }
        }
    }

    /**
     * Indicates whether the link we are looking for.
     *
     * @param attribute the attribute
     * @param id the unique identifier for the target
     * @return true if the condition is met; otherwise false
     */
    @Override
    public boolean isTheLinkWeAreLookingFor(final ApiAttribute attribute, final String id) {
        return "child".equals(attribute.getType()) && attribute.getString().equals(id);
    }
}
