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
    extends OperationsEnabler<Person>
    implements CrudOperations<Person, PersonDocument, ApiPerson>,
        ObjectCrud<ApiPerson> {

    /**
     * Creates a new PersonCrud.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    public PersonCrud(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * Returns the repository.
     *
     * @return the repository
     */
    @Override
    public FindableDocument<Person, PersonDocument> getRepository() {
        return ((PersonDocumentRepositoryMongo) getRepositoryManager().get(Person.class));
    }

    /**
     * Returns the ged class.
     *
     * @return the ged class
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
     * Executes read all.
     *
     * @param db the db
     * @return the resulting list
     */
    @Override
    public List<ApiPerson> readAll(final String db) {
        log.info("Entering read /dbs/{}/persons", db);
        return getD2dm().convert(read(getRepositoryManager(), db));
    }

    /**
     * Executes read one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api person
     */
    @Override
    public ApiPerson readOne(final String db, final String id) {
        log.info("Entering read /dbs/{}/persons/{}", db, id);
        final PersonDocument read = read(getRepositoryManager(), db, id);
        return getD2dm().convert(read);
    }

    /**
     * Executes update one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
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
     * Executes delete one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api person
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
