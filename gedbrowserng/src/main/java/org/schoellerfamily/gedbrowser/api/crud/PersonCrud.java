package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public class PersonCrud
    extends OperationsEnabler<Person, PersonDocument>
    implements CrudOperations<Person, PersonDocument, ApiPerson>,
        ObjectCrud<ApiPerson> {

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public PersonCrud(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FindableDocument<Person, PersonDocument> getRepository() {
        return ((PersonDocumentRepositoryMongo) getRepositoryManager().get(Person.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Person> getGedClass() {
        return Person.class;
    }

    /**
     * Create a new person from the passed object.
     *
     * @param db the name of the db to access
     * @param person the data for the person
     * @return the person as created
     */
    @Override
    public ApiPerson createOne(final String db, final ApiPerson person) {
        log.info("Entering create person in db: {}", db);
        return create(readRoot(getRepositoryManager(), db), person,
            (i, id) -> i
                .toBuilder()
                .string(id)
                .build());
    }

    /**
     * @param db the name of the db to access
     * @return the list of persons
     */
    @Override
    public List<ApiPerson> readAll(final String db) {
        log.info("Entering read /dbs/{}/persons", db);
        return getD2dm().convert(read(getRepositoryManager(), db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the person
     */
    @Override
    public ApiPerson readOne(final String db, final String id) {
        log.info("Entering read /dbs/{}/persons/{}", db, id);
        final PersonDocument read = read(getRepositoryManager(), db, id);
        return getD2dm().convert(read);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person to update
     * @param person the data for the person
     * @return the person as created
     */
    @Override
    public ApiPerson updateOne(final String db, final String id,
            final ApiPerson person) {
        log.info("Entering update person: {} in db: {}", id, db);
        if (!id.equals(person.getString())) {
            return null;
        }
        final ApiPerson changedPerson = person.toBuilder()
            .changed()
            .build();
        return update(readRoot(getRepositoryManager(), db), changedPerson);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the deleted person object
     */
    @Override
    public ApiPerson deleteOne(final String db, final String id) {
        log.info("Entering delete person: {} from db: {}", id, db);
        ApiPerson person = readOne(db, id);
        person = unlinkFamc(db, person);
        /* person = */
        unlinkFams(db, person);
        return delete(readRoot(getRepositoryManager(), db), id);
    }

    /**
     * @param db the dataset
     * @param person the person to modify
     * @return the modified person
     */
    private ApiPerson unlinkFamc(final String db, final ApiPerson person) {
        ApiPerson newPerson = person;
        final List<String> famcList = newPerson.getFamcs().stream()
                .map(ApiAttribute::getString)
                .toList();
        for (final String famc : famcList) {
            newPerson = childCrud().unlinkChild(
                    db, famc, newPerson.getString());
        }
        return newPerson;
    }

    /**
     * @param db the dataset
     * @param person the person to modify
     * @return the modified person
     */
    private ApiPerson unlinkFams(final String db, final ApiPerson person) {
        ApiPerson newPerson = person;
        final List<String> famsList = newPerson.getFamss().stream()
                .map(ApiAttribute::getString)
                .toList();
        for (final String fams : famsList) {
            newPerson = spouseCrud().unlinkSpouseInFamily(
                    db, fams, newPerson.getString());
        }
        return newPerson;
    }

    /**
     * @return a new child CRUD object
     */
    private ChildCrud childCrud() {
        return new ChildCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new spouse CRUD object
     */
    private SpouseCrud spouseCrud() {
        return new SpouseCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }
}
