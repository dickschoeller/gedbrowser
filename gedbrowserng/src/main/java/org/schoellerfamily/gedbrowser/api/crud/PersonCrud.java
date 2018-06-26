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
    implements CrudOperations<Person, PersonDocument, ApiPerson> {
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
    public ApiPerson createPerson(final String db, final ApiPerson person) {
        logger.info("Entering create person in db: " + db);
        return create(readRoot(db), person, (i, id) -> new ApiPerson(i, id));
    }

    /**
     * @param db the name of the db to access
     * @return the list of persons
     */
    public List<ApiPerson> readPersons(final String db) {
        logger.info("Entering read /dbs/" + db + "/persons");
        return getD2dm().convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the person
     */
    public ApiPerson readPerson(final String db, final String id) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id);
        return getD2dm().convert(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person to update
     * @param person the data for the person
     * @return the person as created
     */
    public ApiPerson updatePerson(final String db, final String id,
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
    public ApiPerson deletePerson(final String db, final String id) {
        logger.info("Entering delete person: " + id + " from db: " + db);
        ApiPerson person = readPerson(db, id);
        List<String> famcList = new ArrayList<>();
        for (final ApiAttribute a : person.getFamc()) {
            famcList.add(a.getString());
        }
        for (final String famc : famcList) {
            person = childCrud().unlinkChild(db, famc, id);
        }
        List<String> famsList = new ArrayList<>();
        for (final ApiAttribute a : person.getFams()) {
            famsList.add(a.getString());
        }
        for (final String fams : famsList) {
            spouseCrud().unlinkSpouseInFamily(db, fams, id);
        }
        return delete(readRoot(db), id);
    }

    private ChildCrud childCrud() {
        return new ChildCrud(getLoader(), getConverter(), getRepositoryManager());
    }

    private SpouseCrud spouseCrud() {
        return new SpouseCrud(getLoader(), getConverter(), getRepositoryManager());
    }
}
