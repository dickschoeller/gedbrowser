package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonComparator;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.
    FindableByNameDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class PersonDocumentRepositoryMongoImpl implements
        FindableByNameDocument<Person, PersonDocument>,
        LastId<PersonDocumentMongo> {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;
    /** */
    @Autowired
    private transient GedDocumentMongoToGedObjectConverter toObjConverter;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonDocument findByFileAndString(final String filename,
            final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final PersonDocument personDocument = mongoTemplate.findOne(
                searchQuery, PersonDocumentMongo.class);
        if (personDocument == null) {
            return null;
        }
        final Person person =
                (Person) toObjConverter.createGedObject(null, personDocument);
        personDocument.setGedObject(person);
        return personDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final PersonDocument personDocument = findByFileAndString(
                rootDocument.getFilename(), string);
        if (personDocument == null) {
            return null;
        }
        final Person person = personDocument.getGedObject();
        person.setParent(rootDocument.getGedObject());
        return personDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByFileAndSurname(
            final String filename, final String surname) {
        if (filename == null || surname == null) {
            return Collections.emptyList();
        }
        final Query searchQuery =
                new Query(Criteria.where("surname").is(surname)
                        .and("filename").is(filename));
        final List<PersonDocumentMongo> personDocuments =
                mongoTemplate.find(searchQuery, PersonDocumentMongo.class);
        createGedObjects(personDocuments);
        Collections.sort(personDocuments, new PersonDocumentComparator());
        return copy(personDocuments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByRootAndSurname(
            final RootDocument rootDocument, final String surname) {
        if (rootDocument == null || surname == null) {
            return Collections.emptyList();
        }
        final Collection<PersonDocument> personDocuments = findByFileAndSurname(
                rootDocument.getFilename(), surname);
        for (final PersonDocument personDocument : personDocuments) {
            final Person person = personDocument.getGedObject();
            person.setParent(rootDocument.getGedObject());
        }
        return personDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByFileAndSurnameBeginsWith(
            final String filename, final String beginsWith) {
        if (filename == null) {
            return Collections.emptyList();
        }
        if (beginsWith == null || beginsWith.equals("")
                || beginsWith.equals("?")) {
            final Query emptyQuery = new Query(Criteria.where("surname")
                    .is("").and("filename").is(filename));
            final Query nullQuery = new Query(Criteria.where("surname")
                    .exists(false).and("filename").is(filename));
            final Query qMarkQuery = new Query(Criteria.where("surname")
                    .is("?").and("filename").is(filename));
            final List<PersonDocumentMongo> personDocuments =
                    mongoTemplate.find(emptyQuery, PersonDocumentMongo.class);
            personDocuments.addAll(mongoTemplate.find(nullQuery,
                    PersonDocumentMongo.class));
            personDocuments.addAll(mongoTemplate.find(qMarkQuery,
                    PersonDocumentMongo.class));
            createGedObjects(personDocuments);
            Collections.sort(personDocuments, new PersonDocumentComparator());
            return copy(personDocuments);
        } else {
            final Query searchQuery = new Query(Criteria.where("surname")
                    .regex("^" + beginsWith + ".*").and("filename")
                    .is(filename));
            final List<PersonDocumentMongo> personDocuments =
                    mongoTemplate.find(searchQuery, PersonDocumentMongo.class);
            createGedObjects(personDocuments);
            Collections.sort(personDocuments, new PersonDocumentComparator());
            return copy(personDocuments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByRootAndSurnameBeginsWith(
            final RootDocument rootDocument, final String beginsWith) {
        logger.debug("Starting findByRootAndSurnameBeginsWith");
        if (rootDocument == null) {
            return Collections.emptyList();
        }
        final Collection<PersonDocument> personDocuments =
                findByFileAndSurnameBeginsWith(
                        rootDocument.getFilename(), beginsWith);
        for (final PersonDocument personDocument : personDocuments) {
            final Person person = personDocument.getGedObject();
            person.setParent(rootDocument.getGedObject());
        }
        logger.debug("Ending findByRootAndSurnameBeginsWith");
        return personDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByFile(final String filename) {
        logger.debug("Starting findByFile");
        final Query searchQuery = new Query(Criteria.where("filename").is(
                filename));
        final List<PersonDocumentMongo> personDocuments =
                mongoTemplate.find(searchQuery, PersonDocumentMongo.class);
        createGedObjects(personDocuments);
        Collections.sort(personDocuments, new PersonDocumentComparator());
        logger.debug("Ending findByFile");
        return copy(personDocuments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByRoot(
            final RootDocument rootDocument) {
        logger.info("Starting findByRoot");
        if (rootDocument == null) {
            return Collections.emptyList();
        }
        final Collection<PersonDocument> personDocuments =
                findByFile(rootDocument.getFilename());
        for (final PersonDocument personDocument : personDocuments) {
            final Person person = personDocument.getGedObject();
            person.setParent(rootDocument.getGedObject());
        }
        logger.info("Ending findByRoot");
        return personDocuments;
    }

    /**
     * @param personDocuments
     *            the list of person documents to create and associate.
     */
    private void createGedObjects(
            final List<PersonDocumentMongo> personDocuments) {
        for (final PersonDocumentMongo personDocument : personDocuments) {
            final Person person = (Person) toObjConverter.createGedObject(
                    null, personDocument);
            personDocument.setGedObject(person);
        }
    }

    /**
     * Correct the type of the collection.
     *
     * @param in input collection
     * @return output collection
     */
    private Collection<PersonDocument> copy(
            final Collection<PersonDocumentMongo> in) {
        final List<PersonDocument> out = new ArrayList<>(in.size());
        for (final PersonDocumentMongo pdm : in) {
            out.add(pdm);
        }
        return out;
    }

    /**
     * Standard sorting of Persons.
     *
     * @author Dick Schoeller
     */
    private static class PersonDocumentComparator implements
            Comparator<PersonDocument>, Serializable {
        // TODO combine with the person comparator.

        /** */
        private static final long serialVersionUID = 3;

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(final PersonDocument arg0,
                final PersonDocument arg1) {
            final PersonComparator pc = new PersonComparator();
            return pc.compare(arg0.getGedObject(), arg1.getGedObject());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<PersonDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<PersonDocumentMongo> personDocumentsMongo =
                mongoTemplate.find(searchQuery, PersonDocumentMongo.class);
        if (personDocumentsMongo == null) {
            return null;
        }
        final List<PersonDocument> personDocuments = new ArrayList<>();
        for (final PersonDocument personDocument : personDocumentsMongo) {
            final Person person = (Person) toObjConverter.createGedObject(
                    null, personDocument);
            personDocument.setGedObject(person);
            personDocuments.add(personDocument);
        }
        return personDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<PersonDocument> findAll(final RootDocument rootDocument) {
        final Iterable<PersonDocument> personDocuments =
                findAll(rootDocument.getFilename());
        if (personDocuments == null) {
            return null;
        }
        for (final PersonDocument personDocument : personDocuments) {
            final Person person = personDocument.getGedObject();
            person.setParent(rootDocument.getGedObject());
        }
        return personDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, PersonDocumentMongo.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String lastId(final RootDocument rootDocument) {
        return lastId(mongoTemplate, PersonDocumentMongo.class,
                rootDocument.getFilename(), "I");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String newId(final RootDocument rootDocument) {
        return newId(mongoTemplate, PersonDocumentMongo.class,
                rootDocument.getFilename(), "I");
    }
}
