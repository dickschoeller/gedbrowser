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
 * @author Dick Schoeller
 */
@Slf4j
public class ParentCrud extends RelationsCrud {

    /**
     * @param loader            the file loader that we will use
     * @param toDocConverter    the document converter
     * @param repositoryManager the repository manager
     */
    public ParentCrud(final GedObjectFileLoader loader,
        final GedObjectToGedDocumentMongoConverter toDocConverter,
        final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param db     the name of the db to access
     * @param id     the id of the person whose parent we are adding
     * @param person the data for the parent
     * @return the person returned from the db
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
     * @param db     the name of the db to access
     * @param id     the id of the person whose parent we are adding
     * @param person the data for the spouse
     * @return the person returned from the db
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
     * {@inheritDoc}
     */
    @Override
    public boolean isTheLinkWeAreLookingFor(final ApiAttribute attribute, final String id) {
        return ("wife".equals(attribute.getType()) || "husband".equals(attribute.getType()))
            && attribute.getString().equals(id);
    }
}
