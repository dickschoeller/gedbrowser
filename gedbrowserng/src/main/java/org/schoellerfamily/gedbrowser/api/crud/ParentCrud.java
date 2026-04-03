package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily.ApiFamilyBuilder;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson.ApiPersonBuilder;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

import lombok.extern.slf4j.Slf4j;



/**
 * Represents parent crud.
 *
 * @author Richard Schoeller
 */
@Slf4j
public class ParentCrud extends RelationsCrud {

    /**
     * Creates a new ParentCrud.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    public ParentCrud(final GedObjectFileLoader loader,
        final GedObjectToGedDocumentMongoConverter toDocConverter,
        final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * Creates the parent.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    public ApiPerson createParent(final String db, final String id, final ApiPerson person) {
        log.info("Entering create parent in db: {} for person {}", db, id);
        final ApiPersonBuilder<?, ?> oldPerson = readPerson(db, id).toBuilder();
        final ApiPersonBuilder<?, ?> newPerson = createPerson(db, person).toBuilder();
        final ApiFamilyBuilder<?, ?> family = createFamily(db).toBuilder();
        addChildToFamily(family, oldPerson);
        addSpouseToFamily(family, newPerson);
        return crudUpdate(db, family.build(), oldPerson.build(), newPerson.build());
    }

    /**
     * Executes link parent.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    public ApiPerson linkParent(final String db, final String id, final ApiPerson person) {
        log.info("Entering link person: {} in db: {} as parent of person {}", person.getString(),
            db, id);
        final ApiPersonBuilder<?, ?> oldPerson = readPerson(db, id).toBuilder();
        final ApiPersonBuilder<?, ?> newPerson = readPerson(db, person.getString()).toBuilder();
        final ApiFamilyBuilder<?, ?> family = createFamily(db).toBuilder();
        addChildToFamily(family, oldPerson);
        addSpouseToFamily(family, newPerson);
        return crudUpdate(db, family.build(), oldPerson.build(), newPerson.build());
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
        return ("wife".equals(attribute.getType()) || "husband".equals(attribute.getType()))
            && attribute.getString().equals(id);
    }
}
