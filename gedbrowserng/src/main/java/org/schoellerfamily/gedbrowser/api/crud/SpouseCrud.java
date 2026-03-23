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
 * Represents spouse crud.
 *
 * @author Richard Schoeller
 */
@Slf4j
public class SpouseCrud extends RelationsCrud {

    /**
     * Creates a new SpouseCrud.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    public SpouseCrud(final GedObjectFileLoader loader,
        final GedObjectToGedDocumentMongoConverter toDocConverter,
        final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * Creates the spouse.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    public ApiPerson createSpouse(final String db, final String id, final ApiPerson person) {
        log.info("Entering create spouse in db: {} of person {}", db, id);
        final ApiPersonBuilder<?, ?> oldPerson = readPerson(db, id).toBuilder();
        final ApiPersonBuilder<?, ?> newPerson = createPerson(db, person).toBuilder();
        final ApiFamilyBuilder<?, ?> family = createFamily(db).toBuilder();
        addSpouseToFamily(family, newPerson);
        addSpouseToFamily(family, oldPerson);

        return crudUpdate(db, family.build(), oldPerson.build(), newPerson.build());
    }

    /**
     * Executes link spouse.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    public ApiPerson linkSpouse(final String db, final String id, final ApiPerson person) {
        log.info("Entering link person: {} as spouse in db: {} of person {}", person.getString(),
            db, id);
        final ApiPersonBuilder<?, ?> oldPerson = readPerson(db, id).toBuilder();
        final ApiPersonBuilder<?, ?> newPerson = readPerson(db, person.getString()).toBuilder();
        final ApiFamilyBuilder<?, ?> family = createFamily(db).toBuilder();
        addSpouseToFamily(family, newPerson);
        addSpouseToFamily(family, oldPerson);

        return crudUpdate(db, family.build(), oldPerson.build(), newPerson.build());
    }

    /**
     * Creates the spouse in family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    public ApiPerson createSpouseInFamily(final String db, final String id,
        final ApiPerson person) {
        log.info("Entering create spouse in db: {} in family {}", db, id);
        final ApiPersonBuilder<?, ?> newPerson = createPerson(db, person).toBuilder();
        try {
            final ApiFamilyBuilder<?, ?> family = readFamily(db, id).toBuilder();
            addSpouseToFamily(family, newPerson);
            return crudUpdate(db, family.build(), newPerson.build());
        } catch (ObjectNotFoundException _) {
            return newPerson.build();
        }
    }

    /**
     * Executes link spouse in family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    public ApiPerson linkSpouseInFamily(final String db, final String id, final ApiPerson person) {
        log.info("Entering link person: {} in db: {} as spouse in family {}", person.getString(),
            db, id);
        final ApiPersonBuilder<?, ?> newPerson = readPerson(db, person.getString()).toBuilder();
        try {
            final ApiFamilyBuilder<?, ?> family = readFamily(db, id).toBuilder();
            addSpouseToFamily(family, newPerson);
            return crudUpdate(db, family.build(), newPerson.build());
        } catch (ObjectNotFoundException _) {
            return newPerson.build();
        }
    }

    /**
     * Executes unlink spouse in family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param sid the unique identifier for s
     * @return the resulting api person
     */
    public ApiPerson unlinkSpouseInFamily(final String db, final String id, final String sid) {
        log.info("Entering unlink person: {} in db: {} from being a spouse in family {}", sid, db,
            id);
        final ApiPersonBuilder<?, ?> person = readPerson(db, sid).toBuilder();
        try {
            final ApiFamilyBuilder<?, ?> family = readFamily(db, id).toBuilder();
            removeSpouseFromFamily(family, person);
            return crudUpdate(db, family.build(), person.build());
        } catch (ObjectNotFoundException _) {
            final ApiAttribute fams = findFamsAttribute(id, person);
            person.getFamss().remove(fams);
            crudUpdate(db, person.build());
            return person.build();
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
        return ("husband".equals(attribute.getType()) || "wife".equals(attribute.getType()))
            && attribute.getString().equals(id);
    }
}
