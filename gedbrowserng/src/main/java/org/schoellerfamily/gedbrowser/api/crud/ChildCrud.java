package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public final class ChildCrud extends RelationsCrud {

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
        log.info("Entering create child in db: {} for person {}", db, id);
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
        log.info("Entering link person: {} in db: {} as a child of person: {}, creating a new family", person.getString(), db, id);
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
        log.info("Entering create child in db: {} for family {}", db, id);
        try {
            final ApiFamily family = readFamily(db, id);
            final ApiPerson newPerson = createPerson(db, person);
            addChildToFamily(family, newPerson);
            return crudUpdate(db, family, newPerson);
        } catch (ObjectNotFoundException e) {
            return null;
        }
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family whose child we are linking
     * @param person the data for the child (only need the string field)
     * @return the person linking to the new person
     */
    public ApiPerson linkChildInFamily(final String db, final String id,
            final ApiPerson person) {
        log.info("Entering link person: {} in db: {} as a child of family: {}", person.getString(), db, id);
        final ApiPerson foundPerson = readPerson(db, person.getString());
        try {
            final ApiFamily family = readFamily(db, id);
            addChildToFamily(family, foundPerson);
            return crudUpdate(db, family, foundPerson);
        } catch (ObjectNotFoundException e) {
            return foundPerson;
        }
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family whose child we are unlinking
     * @param child the id of the child to unlink
     * @return the person linking to the new person
     */
    public ApiPerson unlinkChild(final String db, final String id,
            final String child) {
        log.info("Entering unlink person: {} in db: {} from family: {}", child, db, id);
        final ApiPerson childPerson = readPerson(db, child);
        removeFamilyFromChild(id, childPerson);
        try {
            final ApiFamily family = readFamily(db, id);
            removeChildFromFamily(family, child);
            return crudUpdate(db, family, childPerson);
        } catch (ObjectNotFoundException e) {
            return crudUpdate(db, childPerson);
        }
    }

    /**
     * @param fid the family referred to by the famc link to remove
     * @param person the person to remove from
     */
    protected void removeFamilyFromChild(final String fid,
            final ApiPerson person) {
        for (final ApiAttribute famc : person.getFamc()) {
            if (famc.getString().equals(fid)) {
                person.getFamc().remove(famc);
                break;
            }
        }
    }

    /**
     * @param family the family to remove from
     * @param cid the person who referred to by the child link to remove
     */
    private void removeChildFromFamily(final ApiFamily family,
            final String cid) {
        for (final ApiAttribute childlink : family.getChildren()) {
            if (childlink.getString().equals(cid)) {
                family.getChildren().remove(childlink);
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTheLinkWeAreLookingFor(final ApiAttribute attribute,
            final String id) {
        return "child".equals(attribute.getType())
                && attribute.getString().equals(id);
    }
}
