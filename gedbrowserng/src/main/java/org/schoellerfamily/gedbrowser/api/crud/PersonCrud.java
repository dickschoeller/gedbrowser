package org.schoellerfamily.gedbrowser.api.crud;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * @author Dick Schoeller
 */
public class PersonCrud
    extends OperationsEnabler<Person, PersonDocument>
    implements CrudOperations<Person, PersonDocument, ApiPerson>,
        ObjectCrud<ApiPerson> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public PersonCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FindableDocument<Person, PersonDocument> getRepository() {
        return getRepositoryManager().getPersonDocumentRepository();
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
        logger.info("Entering create person in db: " + db);
        return create(readRoot(db), person, (i, id) -> new ApiPerson(i, id));
    }

    /**
     * @param db the name of the db to access
     * @return the list of persons
     */
    @Override
    public List<ApiPerson> readAll(final String db) {
        logger.info("Entering read /dbs/" + db + "/persons");
        return getD2dm().convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the person
     */
    @Override
    public ApiPerson readOne(final String db, final String id) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id);
        final PersonDocument read = read(db, id);
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
        logger.info("Entering update person: " + id + " in db: " + db);
        if (!id.equals(person.getString())) {
            return null;
        }
        person.change();
        return update(readRoot(db), person);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the deleted person object
     */
    @Override
    public ApiPerson deleteOne(final String db, final String id) {
        logger.info("Entering delete person: " + id + " from db: " + db);
        ApiPerson person = readOne(db, id);
        person = unlinkFamc(db, person);
        /* person = */
        unlinkFams(db, person);
        return delete(readRoot(db), id);
    }

    /**
     * @param db the dataset
     * @param person the person to modify
     * @return the modified person
     */
    private ApiPerson unlinkFamc(final String db, final ApiPerson person) {
        final List<String> famcList = new ArrayList<>();
        ApiPerson newPerson = person;
        for (final ApiAttribute a : newPerson.getFamc()) {
            famcList.add(a.getString());
        }
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
        final List<String> famsList = new ArrayList<>();
        ApiPerson newPerson = person;
        for (final ApiAttribute a : newPerson.getFams()) {
            famsList.add(a.getString());
        }
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
